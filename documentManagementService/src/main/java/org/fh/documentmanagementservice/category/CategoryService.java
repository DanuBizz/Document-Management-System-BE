package org.fh.documentmanagementservice.category;

import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service for Category related operations.
 * It handles the business logic for the Category entity.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;  // Assuming there's a UserRepository handling User entities

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        List<User> userList = userRepository.findAllById(categoryRequestDTO.getUserIds());
        Set<User> users = new HashSet<>(userList);
        category.setUsers(users);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = getCategoryById(id)
                .orElseThrow(ResourceNotFoundException::new);
        category.setName(categoryRequestDTO.getName());
        category.getUsers().clear();
        List<User> userList = userRepository.findAllById(categoryRequestDTO.getUserIds());
        Set<User> users = new HashSet<>(userList);
        category.setUsers(users);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}