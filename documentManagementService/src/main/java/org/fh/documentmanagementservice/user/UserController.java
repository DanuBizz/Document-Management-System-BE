package org.fh.documentmanagementservice.user;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        Page<UserResponseDTO> userPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // GET one user
    // data is additionally base64 encoded and has to be decoded manually
    @GetMapping(path = "user/coded/{encodedUsername}")
    public User getUserData(@PathVariable String encodedUsername) throws Exception {
        byte[] decoded = Base64.decodeBase64(encodedUsername);
        System.out.println(decoded);
        return userService.getUserData(new String(decoded, StandardCharsets.UTF_8));
    }

    @GetMapping(path = "activeDirectory/all")
    public List<User> getAllActiveDirectoryUserData() throws Exception {
        return userService.getAllActiveDirectoryUserData();
    }

    // GET all user data
    @GetMapping(path = "admin/{id}")
    public List<User> getAllUsersData(@PathVariable long id) {
        return userService.getAllUsersData(id);
    }

    // GET username
    @GetMapping(path = "name/{id}")
    public UserNameDTO getUsername(@PathVariable long id) {
        return userService.getUsername(id);
    }

    // GET all usernames
    @GetMapping(path = "name/all")
    public List<UserNameDTO> getAllUsernames() {
        return userService.getAllUsernames();
    }

    // GET username from list
    @PostMapping(path = "name/list")
    public List<UserNameDTO> getUsernamesFromList(@RequestBody List<UserNameDTO> userNameDTOList) {
        return userService.getUsernameFromList(userNameDTOList);
    }
}