package org.fh.documentmanagementservice.userDocumentRead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for UserDocument related operations.
 * This interface extends JpaRepository and includes methods for UserDocument related database operations.
 */
@Repository
public interface UserDocumentReadRepository extends JpaRepository<UserDocumentRead, Long> {
}