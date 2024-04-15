package org.fh.documentmanagementservice.category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Category related operations.
 * It extends JpaRepository to provide CRUD operations for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}