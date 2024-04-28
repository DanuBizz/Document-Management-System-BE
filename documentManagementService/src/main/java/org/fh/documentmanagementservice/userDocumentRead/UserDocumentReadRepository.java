package org.fh.documentmanagementservice.userDocumentRead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserDocument related operations.
 * This interface extends JpaRepository and includes methods for UserDocument related database operations.
 */
@Repository
public interface UserDocumentReadRepository extends JpaRepository<UserDocumentRead, Long> {

    /**
     * Finds a UserDocument by User ID and Document ID.
     * It accepts the User ID and Document ID and returns an Optional of UserDocument.
     * @param userId The ID of the User.
     * @param documentId The ID of the Document.
     * @return An Optional of UserDocument.
     */
    Optional<UserDocumentRead> findByUserIdAndDocumentId(Long userId, Long documentId);
}