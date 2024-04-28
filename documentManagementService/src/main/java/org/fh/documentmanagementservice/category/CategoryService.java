package org.fh.documentmanagementservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Category related operations.
 * It handles the business logic for the Category entity.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = getCategoryById(id)
                .orElseThrow(ResourceNotFoundException::new);
        category.setName(categoryRequestDTO.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}