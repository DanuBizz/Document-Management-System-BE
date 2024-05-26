package org.fh.documentmanagementservice.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Document related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on Document entities.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);

    Page<Document> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}