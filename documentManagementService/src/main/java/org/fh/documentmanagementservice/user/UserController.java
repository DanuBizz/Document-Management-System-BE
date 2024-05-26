package org.fh.documentmanagementservice.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/toggle-admin")
    public ResponseEntity<UserResponseDTO> toggleUserAdminStatus(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.toggleUserAdminStatus(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchUsers(@RequestParam String search, Pageable pageable) {
        Page<UserResponseDTO> userPage = userService.searchUsers(search, pageable);
        return ResponseEntity.ok(userPage);
    }

    @PutMapping("/{id}/groups")
    public ResponseEntity<Void> updateUserGroups(@PathVariable Long id, @RequestBody List<String> groupNames) {
        userService.updateUserGroups(id, groupNames);
        return ResponseEntity.noContent().build();
    }
}