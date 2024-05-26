package org.fh.documentmanagementservice.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Category related operations.
 * It extends JpaRepository to provide CRUD operations for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}