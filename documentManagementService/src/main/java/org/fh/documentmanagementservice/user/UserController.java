package org.fh.documentmanagementservice.user;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Controller for User related operations.
 * It handles the HTTP requests and responses for the User entity.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Create a new User.
     * @param userRequestDTO The UserRequestDTO object containing the User data.
     * @return The UserResponseDTO object containing the created User data.
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }
    /**
     * Get all Users.
     * @param pageable The Pageable object containing the pagination data.
     * @return The Page object containing the User data.
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        Page<UserResponseDTO> userPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userPage);
    }
    /**
     * Get a User by ID.
     * @param id The ID of the User.
     * @return The UserResponseDTO object containing the User data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    /**
     * Update a User.
     * @param id The ID of the User.
     * @param userRequestDTO The UserRequestDTO object containing the User data.
     * @return The UserResponseDTO object containing the updated User data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }
    /**
     * Delete a User.
     * @param id The ID of the User.
     * @return The ResponseEntity object.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Get all User data.
     * @param encodedUsername The encoded username.
     * @return User
     * @throws Exception Exception
     */
    // GET one user
    // data is additionally base64 encoded and has to be decoded manually
    @GetMapping(path = "user/coded/{encodedUsername}")
    public User getUserData(@PathVariable String encodedUsername) throws Exception {
        byte[] decoded = Base64.decodeBase64(encodedUsername);
        System.out.println(decoded);
        return userService.getUserData(new String(decoded, StandardCharsets.UTF_8));
    }
    /**
     * Get all Active Directory User data.
     * @return
     * @throws Exception
     */
    @GetMapping(path = "activeDirectory/all")
    public List<User> getAllActiveDirectoryUserData() throws Exception {
        return userService.getAllActiveDirectoryUserData();
    }
    /**
     * Get all User data.
     * @param id
     * @return
     */
    // GET all user data
    @GetMapping(path = "admin/{id}")
    public List<User> getAllUsersData(@PathVariable long id) {
        return userService.getAllUsersData(id);
    }
    /**
     * Get all User data.
     * @param id
     * @return
     */
    // GET username
    @GetMapping(path = "name/{id}")
    public UserNameDTO getUsername(@PathVariable long id) {
        return userService.getUsername(id);
    }
    /**
     * Get all Usernames.
     * @return
     */
    // GET all usernames
    @GetMapping(path = "name/all")
    public List<UserNameDTO> getAllUsernames() {
        return userService.getAllUsernames();
    }
    /**
     * Get all Usernames from list.
     * @param userNameDTOList
     * @return
     */
    // GET username from list
    @PostMapping(path = "name/list")
    public List<UserNameDTO> getUsernamesFromList(@RequestBody List<UserNameDTO> userNameDTOList) {
        return userService.getUsernameFromList(userNameDTOList);
    }
    /**
     * Get all User data. (Admin)
     * @param id
     * @return
     */
    @PutMapping("/{id}/toggle-admin")
    public ResponseEntity<UserResponseDTO> toggleUserAdminStatus(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.toggleUserAdminStatus(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    /**
     * Search for Users.
     * @param search
     * @param pageable
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchUsers(@RequestParam String search, Pageable pageable) {
        Page<UserResponseDTO> userPage = userService.searchUsers(search, pageable);
        return ResponseEntity.ok(userPage);
    }
    /**
     * Update User Groups.
     * @param id
     * @param groupIds
     * @return
     */
    @PutMapping("/{id}/groups")
    public ResponseEntity<Void> updateUserGroups(@PathVariable Long id, @RequestBody List<Long> groupIds) {
        userService.updateUserGroups(id, groupIds);
        return ResponseEntity.noContent().build();
    }
}