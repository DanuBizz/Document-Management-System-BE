package org.fh.documentmanagementservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for User related operations.
 * It handles the HTTP requests and responses for the User entity.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Service for User related operations.
     * It is automatically injected by Spring Boot.
     */
    @Autowired
    private UserService userService;

    /**
     * Retrieves all Users.
     * It accepts a Pageable object and returns a ResponseEntity with a Page of UserResponseDTO objects.
     * @param pageable The pagination information.
     * @return A ResponseEntity with a Page of UserResponseDTO objects.
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Creates a new User.
     * It accepts a UserRequestDTO object and returns a UserResponseDTO object.
     * @param userRequestDTO The request object containing the details of the new User.
     * @return The response object of the created User.
     */
    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    /**
     * Retrieves a User by its ID.
     * It accepts the ID of the User and returns a UserResponseDTO object.
     * @param id The ID of the User to retrieve.
     * @return The UserResponseDTO object of the retrieved User.
     */
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * Updates a User.
     * It accepts the ID of the User and a UserRequestDTO object, and returns a UserResponseDTO object.
     * @param id The ID of the User to update.
     * @param userRequestDTO The request object containing the new details of the User.
     * @return The UserResponseDTO object of the updated User.
     */
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    /**
     * Deletes a User.
     * It accepts the ID of the User to delete.
     * @param id The ID of the User to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}