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
import java.util.*;

@Service
public class UserService {

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

    private final UserRepository userRepository;
    /**
     * Constructor injecting UserRepository dependency.
     *
     * @param userRepository Repository for User entities.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Creates a new user.
     *
     * @param userRequestDTO User data for creation.
     * @return UserResponseDTO representing the created user.
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setIsAdmin(userRequestDTO.getIsAdmin());

        User savedUser = userRepository.save(user);

        return convertToUserResponseDTO(savedUser);
    }
    /**
     * Retrieves all users with pagination support.
     *
     * @param pageable Pagination information.
     * @return Page of UserResponseDTO representing users.
     */
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(this::convertToUserResponseDTO);
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return UserResponseDTO representing the user.
     * @throws RuntimeException if the user with the given ID is not found.
     */
    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return convertToUserResponseDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    /**
     * Updates an existing user.
     *
     * @param id             The ID of the user to update.
     * @param userRequestDTO Updated user data.
     * @return UserResponseDTO representing the updated user.
     * @throws RuntimeException if the user with the given ID is not found.
     */
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
    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws RuntimeException if the user with the given ID is not found.
     */
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
    /**
     * Retrieves user data either from the database or Active Directory based on the username.
     *
     * @param username The username of the user to retrieve.
     * @return User object representing the user data.
     * @throws Exception if the user data cannot be retrieved.
     */
    // GET or create one user, if it doesn't exist already
    public User getUserData(String username) throws Exception {
        User user = userRepository.findByUserUsername(username);
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
    /**
     * Retrieves all user data from Active Directory.
     *
     * @return List of User objects representing Active Directory users.
     * @throws Exception if user data cannot be retrieved.
     */
    // GET all active directory members
    public List<User> getAllActiveDirectoryUserData() throws Exception {
        List<User> activeDirectoryUserList = new ArrayList<>();
        addAllActiveDirectoryMembersToList(activeDirectoryUserList);
        return activeDirectoryUserList;
    }
    /**
     * Retrieves all user data.
     *
     * @param id The ID of the user making the request.
     * @return List of User objects representing all users if the requesting user is an admin, null otherwise.
     */
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
    /**
     * Retrieves the username of a user by their ID.
     *
     * @param id The ID of the user.
     * @return UserNameDTO containing the user's ID and username.
     */
    // GET users username
    public UserNameDTO getUsername(long id) {
        User user = userRepository.findByUserId(id);
        return new UserNameDTO(user.getId(), user.getUsername());
    }
    /**
     * Retrieves usernames of all users.
     *
     * @return List of UserNameDTO containing user IDs and usernames.
     */
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
    /**
     * Retrieves usernames from a list of UserNameDTO objects.
     *
     * @param userNameDTOList List of UserNameDTO objects.
     * @return List of UserNameDTO with names populated.
     */
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
        //String username = activeDirectoryBindingUser;
        //String password = readActiveDirectoryBindingPassword();

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:8389/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, username);
        //env.put(Context.SECURITY_CREDENTIALS, password);

        return new InitialDirContext(env);
    }

    private NamingEnumeration<SearchResult> getActiveDirectorySearchResult(DirContext context) throws NamingException {
        String searchBase = activeDirectorySearchBase;
        String searchFilter = activeDirectorySearchFilter;

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributesToRetrieve = {
                //"userPrincipalName",    // user logon name
                //"userAccountControl",   // if user is activated or deactivated
                "givenName",            // first name
                "sn",                   // last name
                "mail"                  // e-mail address
        };
        controls.setReturningAttributes(attributesToRetrieve);

        return context.search(searchBase, searchFilter, controls);
    }

    private User getUserDataFromActiveDirectorySearchResult(SearchResult searchResult) throws NamingException {
        Attributes attributes = searchResult.getAttributes();

        /*Attribute userPrincipalNameAttr = attributes.get("userPrincipalName");
        String userPrincipalName = (userPrincipalNameAttr != null) ? (String) userPrincipalNameAttr.get() : null;
        if (userPrincipalName != null) {
            userPrincipalName = userPrincipalName.split("@")[0];
        }*/
        Attribute firstNameAttr = attributes.get("givenName");
        String firstName = (firstNameAttr != null) ? (String) firstNameAttr.get() : null;

        //Attribute lastNameAttr = attributes.get("sn");
        //String lastName = (lastNameAttr != null) ? (String) lastNameAttr.get() : null;
        Attribute emailAttr = attributes.get("mail");
        String email = (emailAttr != null) ? (String) emailAttr.get() : null;

        return new User(firstName, email, false);
    }

}