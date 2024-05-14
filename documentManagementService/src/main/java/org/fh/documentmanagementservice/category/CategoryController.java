package org.fh.documentmanagementservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.fh.documentmanagementservice.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for Category related operations.
 * It handles HTTP requests and responses for the Category entity.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryService.getAllCategories(pageable);
        Page<CategoryResponseDTO> dtoPage = categoryPage.map(category -> {
            List<Long> userIds = category.getUsers().stream().map(User::getId).collect(Collectors.toList());
            List<String> userNames = category.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
            return new CategoryResponseDTO(category.getId(), category.getName(), userIds, userNames);
        });
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(ResourceNotFoundException::new);
        List<Long> userIds = category.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<String> userNames = category.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                category.getId(), category.getName(), userIds, userNames);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryService.createCategory(categoryRequestDTO);
        List<Long> userIds = category.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<String> userNames = category.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                category.getId(), category.getName(), userIds, userNames);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoryUpdateDTO);
        List<Long> userIds = updatedCategory.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<String> userNames = updatedCategory.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                updatedCategory.getId(), updatedCategory.getName(), userIds, userNames);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
