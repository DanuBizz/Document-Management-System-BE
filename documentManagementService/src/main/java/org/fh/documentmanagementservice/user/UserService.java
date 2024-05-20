package org.fh.documentmanagementservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setIsAdmin(userRequestDTO.getIsAdmin());

        User savedUser = userRepository.save(user);

        return convertToUserResponseDTO(savedUser);
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(this::convertToUserResponseDTO);
    }

    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return convertToUserResponseDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public UserResponseDTO findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        if (userOptional.isPresent()) {
            return convertToUserResponseDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequestDTO.getUsername());
            user.setEmail(userRequestDTO.getEmail());
            user.setIsAdmin(userRequestDTO.getIsAdmin());

            User updatedUser = userRepository.save(user);

            return convertToUserResponseDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setIsAdmin(user.getIsAdmin());

        return userResponseDTO;
    }

    @Value("${active.directory.url}")
    private String activeDirectoryUrl;

    @Value("${active.directory.search.base}")
    private String activeDirectorySearchBase;

    @Value("${active.directory.search.filter}")
    private String activeDirectorySearchFilter;

    @Value("${active.directory.binding.user}")
    private String activeDirectoryBindingUser;

    @Value("${path.to.active-directory-binding-pwd}")
    private String pathToActiveDirectoryBindingPwdCsv;

    // GET or create one user, if it doesn't exist already
    public User getUserData(String username) throws Exception {
        User user = userRepository.findByUserLogonName(username);
        if (user != null) {
            return userRepository.saveAndFlush(user);
        } else {
            User activeDirectoryUserData = getActiveDirectoryUserData(username);
            return userRepository.saveAndFlush(
                    new User(
                            activeDirectoryUserData.getUsername(),
                            activeDirectoryUserData.getEmail(),
                            false
                    )
            );
        }
    }

    // GET all active directory members
    public List<User> getAllActiveDirectoryUserData() throws Exception {
        List<User> activeDirectoryUserList = new ArrayList<>();
        addAllActiveDirectoryMembersToList(activeDirectoryUserList);
        return activeDirectoryUserList;
    }

    // GET all user data
    // checks if user is super admin and returns all users if true
    public List<User> getAllUsersData(Long id) {
        User admin = userRepository.findByUserId(id);
        if (admin.getIsAdmin()) {
            List<User> allUsers = userRepository.findAll();
            allUsers.sort(Comparator.naturalOrder());
            return allUsers;
        } else {
            return null;
        }
    }

    // GET users username
    public UserNameDTO getUsername(long id) {
        User user = userRepository.findByUserId(id);
        return new UserNameDTO(user.getId(), user.getUsername());
    }

    // GET all users usernames
    public List<UserNameDTO> getAllUsernames() {
        List<User> allUsersList = userRepository.findAll();
        List<UserNameDTO> allUsernamesDTOList = new ArrayList<>();
        for (User user : allUsersList) {
            allUsernamesDTOList.add(new UserNameDTO(user.getId(), user.getUsername()));
        }
        allUsernamesDTOList.sort(Comparator.naturalOrder());
        return allUsernamesDTOList;
    }

    // GET all usernames from list
    public List<UserNameDTO> getUsernameFromList(List<UserNameDTO> userNameDTOList) {
        for (UserNameDTO userNameDTO : userNameDTOList) {
            User user = userRepository.findByUserId(userNameDTO.getId());
            userNameDTO.setName(user.getUsername());
        }
        userNameDTOList.sort(Comparator.naturalOrder());
        return userNameDTOList;
    }

    // ####################### Active Directory #######################

    private User getActiveDirectoryUserData(String username) throws Exception {
        DirContext context = getActiveDirectoryContext();
        NamingEnumeration<SearchResult> results = getActiveDirectorySearchResult(context);
        while (results.hasMore()) {
            User user = getUserDataFromActiveDirectorySearchResult(results.next());
            if (username.equalsIgnoreCase(user.getUsername())) {
                context.close();
                return user;
            }
        }
        context.close();
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found in Active Directory!");
    }

    private void addAllActiveDirectoryMembersToList(List<User> activeDirectoryUserList) throws Exception {
        DirContext context = getActiveDirectoryContext();
        NamingEnumeration<SearchResult> results = getActiveDirectorySearchResult(context);
        while (results.hasMore()) {
            User user = getUserDataFromActiveDirectorySearchResult(results.next());
            activeDirectoryUserList.add(user);
        }
        context.close();
    }

    private DirContext getActiveDirectoryContext() throws Exception {
        String username = activeDirectoryBindingUser;
        String password = readActiveDirectoryBindingPassword();

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, activeDirectoryUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);

        return new InitialDirContext(env);
    }

    private String readActiveDirectoryBindingPassword() throws Exception {
        BufferedReader br = new BufferedReader(new java.io.FileReader(pathToActiveDirectoryBindingPwdCsv));
        String password = br.readLine();
        System.out.println("Password: " + password);
        br.close();
        return password;
    }

    private NamingEnumeration<SearchResult> getActiveDirectorySearchResult(DirContext context) throws NamingException {
        String searchBase = activeDirectorySearchBase;
        String searchFilter = activeDirectorySearchFilter;

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributesToRetrieve = {
                "userPrincipalName",    // user logon name
                "userAccountControl",   // if user is activated or deactivated
                "givenName",            // first name
                "sn",                   // last name
                "mail"                  // e-mail address
        };
        controls.setReturningAttributes(attributesToRetrieve);

        return context.search(searchBase, searchFilter, controls);
    }

    private User getUserDataFromActiveDirectorySearchResult(SearchResult searchResult) throws NamingException {
        Attributes attributes = searchResult.getAttributes();

        Attribute userPrincipalNameAttr = attributes.get("userPrincipalName");
        String userPrincipalName = (userPrincipalNameAttr != null) ? (String) userPrincipalNameAttr.get() : null;
        if (userPrincipalName != null) {
            userPrincipalName = userPrincipalName.split("@")[0];
        }

        Attribute emailAttr = attributes.get("mail");
        String email = (emailAttr != null) ? (String) emailAttr.get() : null;

        return new User(userPrincipalName, email, false);
    }

}