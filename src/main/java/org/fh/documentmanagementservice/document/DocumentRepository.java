package org.fh.documentmanagementservice.document;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Document related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on Document entities.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
}