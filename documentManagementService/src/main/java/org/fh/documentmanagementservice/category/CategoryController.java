package org.fh.documentmanagementservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Category related operations.
 * It handles HTTP requests and responses for the Category entity.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    /**
     * Service for Category related operations.
     * It is automatically injected by Spring Boot.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Endpoint for creating a new Category.
     * It accepts a POST request with a CategoryRequestDTO object in the request body.
     * @param categoryRequestDTO The request body containing the details of the new Category.
     * @return The created CategoryResponseDTO object.
     */
    @PostMapping
    public CategoryResponseDTO createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.createCategory(categoryRequestDTO);
    }

    /**
     * Endpoint for retrieving all Categories.
     * It accepts a GET request and returns a Page of CategoryResponseDTO objects.
     * @param pageable The pagination information.
     * @return A ResponseEntity containing a Page of CategoryResponseDTO objects.
     */
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<CategoryResponseDTO> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    /**
     * Endpoint for retrieving a Category by its ID.
     * It accepts a GET request with the ID as a path variable.
     * @param id The ID of the Category to retrieve.
     * @return The CategoryResponseDTO object of the retrieved Category.
     */
    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Endpoint for updating a Category.
     * It accepts a PUT request with the ID as a path variable and a CategoryRequestDTO object in the request body.
     * @param id The ID of the Category to update.
     * @param categoryRequestDTO The request body containing the new details of the Category.
     * @return The CategoryResponseDTO object of the updated Category.
     */
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.updateCategory(id, categoryRequestDTO);
    }

    /**
     * Endpoint for deleting a Category.
     * It accepts a DELETE request with the ID as a path variable.
     * @param id The ID of the Category to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}