package org.fh.documentmanagementservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is the REST controller for the category web service.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    /**
     * Create a category.
     * @param categoryRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }
    /**
     * Update a category.
     * @param id
     * @param categoryRequestDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }
    /**
     * Get a category by id.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponseDTO);
    }
    /**
     * Get all categories.
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<CategoryResponseDTO> categoryPage = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categoryPage);
    }
    /**
     * Delete a category.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Search categories.
     * @param search
     * @param pageable
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Page<CategoryResponseDTO>> searchCategories(@RequestParam String search, Pageable pageable) {
        Page<CategoryResponseDTO> categoryPage = categoryService.searchCategories(search, pageable);
        return ResponseEntity.ok(categoryPage);
    }
}
