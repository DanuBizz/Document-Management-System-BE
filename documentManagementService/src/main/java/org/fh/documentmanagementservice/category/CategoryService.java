package org.fh.documentmanagementservice.category;

import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for Category related operations.
 * It handles the business logic for the Category entity.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Creates a new Category.
     * It accepts a CategoryRequestDTO object and returns a CategoryResponseDTO object.
     * @param categoryRequestDTO The request object containing the details of the new Category.
     * @return The response object of the created Category.
     */
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setUsers(convertUserIdsToUsers(categoryRequestDTO.getUserIDs()));
        category.setDocuments(convertDocumentIdsToDocuments(categoryRequestDTO.getDocumentIDs()));
        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryResponseDTO(savedCategory);
    }

    /**
     * Retrieves all Categories.
     * It accepts a Pageable object and returns a Page of CategoryResponseDTO objects.
     * @param pageable The pagination information.
     * @return A Page of CategoryResponseDTO objects.
     */
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(this::convertToCategoryResponseDTO);
    }

    /**
     * Retrieves a Category by its ID.
     * It accepts the ID of the Category and returns a CategoryResponseDTO object.
     * @param id The ID of the Category to retrieve.
     * @return The CategoryResponseDTO object of the retrieved Category.
     */
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToCategoryResponseDTO(category);
    }

    /**
     * Updates a Category.
     * It accepts the ID of the Category and a CategoryRequestDTO object, and returns a CategoryResponseDTO object.
     * @param id The ID of the Category to update.
     * @param categoryRequestDTO The request object containing the new details of the Category.
     * @return The CategoryResponseDTO object of the updated Category.
     */
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryRequestDTO.getName());

        // Properly handle the users set
        if (category.getUsers() == null) {
            category.setUsers(new HashSet<>());
        } else {
            category.getUsers().clear(); // Clear the existing set to avoid UnsupportedOperationException
        }
        category.getUsers().addAll(convertUserIdsToUsers(categoryRequestDTO.getUserIDs())); // Re-add the users

        // Properly handle the documents set
        if (category.getDocuments() == null) {
            category.setDocuments(new HashSet<>());
        } else {
            category.getDocuments().clear(); // Clear the existing set to avoid UnsupportedOperationException
        }
        category.getDocuments().addAll(convertDocumentIdsToDocuments(categoryRequestDTO.getDocumentIDs())); // Re-add the documents

        Category updatedCategory = categoryRepository.save(category);
        return convertToCategoryResponseDTO(updatedCategory);
    }


    /**
     * Deletes a Category.
     * It accepts the ID of the Category to delete.
     * @param id The ID of the Category to delete.
     */
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Converts a set of user IDs to a set of User objects.
     * It accepts a set of user IDs and returns a set of User objects.
     * @param userIds The set of user IDs to convert.
     * @return The set of User objects.
     */
    public Set<User> convertUserIdsToUsers(Set<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return Set.of();
        return userIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + id)))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a set of document IDs to a set of Document objects.
     * It accepts a set of document IDs and returns a set of Document objects.
     * @param documentIds The set of document IDs to convert.
     * @return The set of Document objects.
     */
    public Set<Document> convertDocumentIdsToDocuments(Set<Long> documentIds) {
        if (documentIds == null || documentIds.isEmpty()) return Set.of();
        return documentIds.stream()
                .map(id -> documentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Document not found with ID: " + id)))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a Category object to a CategoryResponseDTO object.
     * It accepts a Category object and returns a CategoryResponseDTO object.
     * @param category The Category object to convert.
     * @return The CategoryResponseDTO object.
     */
    private CategoryResponseDTO convertToCategoryResponseDTO(Category category) {
        Set<Long> userIds = category.getUsers().stream().map(User::getId).collect(Collectors.toSet());
        Set<Long> documentIds = category.getDocuments().stream().map(Document::getId).collect(Collectors.toSet());
        return new CategoryResponseDTO(category.getId(), category.getName(), userIds, documentIds);
    }
}
