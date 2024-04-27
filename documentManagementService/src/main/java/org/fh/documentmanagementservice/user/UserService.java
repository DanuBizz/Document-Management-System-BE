package org.fh.documentmanagementservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for User related operations.
 * This class handles the business logic for the User entity.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new User.
     * It accepts a UserRequestDTO object and returns a UserResponseDTO object.
     * @param userRequestDTO The request object containing the details of the new User.
     * @return The response object of the created User.
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setIsAdmin(userRequestDTO.getIsAdmin());
        user.setCategories(convertCategoryIdsToCategories(userRequestDTO.getCategoryIds()));
        User savedUser = userRepository.save(user);
        return convertToUserResponseDTO(savedUser);
    }

    /**
     * Retrieves all Users.
     * It accepts a Pageable object and returns a Page of UserResponseDTO objects.
     * @param pageable The pagination information.
     * @return A Page of UserResponseDTO objects.
     */
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToUserResponseDTO);
    }


    /**
     * Retrieves a User by its ID.
     * It accepts the ID of the User and returns a UserResponseDTO object.
     * @param id The ID of the User to retrieve.
     * @return The UserResponseDTO object of the retrieved User.
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToUserResponseDTO(user);
    }

    /**
     * Updates a User.
     * It accepts the ID of the User and a UserRequestDTO object, and returns a UserResponseDTO object.
     * @param id The ID of the User to update.
     * @param userRequestDTO The request object containing the new details of the User.
     * @return The UserResponseDTO object of the updated User.
     */
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setIsAdmin(userRequestDTO.getIsAdmin());

        // Properly handle the categories set
        if (user.getCategories() == null) {
            user.setCategories(new HashSet<>());
        } else {
            user.getCategories().clear(); // Clear the existing set to avoid UnsupportedOperationException
        }
        user.getCategories().addAll(convertCategoryIdsToCategories(userRequestDTO.getCategoryIds())); // Re-add the categories

        User updatedUser = userRepository.save(user);
        return convertToUserResponseDTO(updatedUser);
    }


    /**
     * Deletes a User.
     * It accepts the ID of the User to delete.
     * @param id The ID of the User to delete.
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Converts a set of category IDs to a set of Category objects.
     * It accepts a set of category IDs and returns a set of Category objects.
     * @param categoryIds The set of category IDs to convert.
     * @return A set of Category objects.
     */
    private Set<Category> convertCategoryIdsToCategories(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return Set.of();
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id)))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a User object to a UserResponseDTO object.
     * It accepts a User object and returns a UserResponseDTO object.
     * @param user The User object to convert.
     * @return The UserResponseDTO object.
     */
    private UserResponseDTO convertToUserResponseDTO(User user) {
        Set<Long> categoryIds = user.getCategories() != null ? user.getCategories().stream().map(Category::getId).collect(Collectors.toSet()) : Set.of();
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getIsAdmin(), categoryIds);
    }
}
