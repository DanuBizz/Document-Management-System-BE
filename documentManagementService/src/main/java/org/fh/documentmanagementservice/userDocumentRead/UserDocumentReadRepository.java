package org.fh.documentmanagementservice.userDocumentRead;

import org.fh.documentmanagementservice.documentVersion.DocumentVersion;
import org.fh.documentmanagementservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for UserDocument related operations.
 * This interface extends JpaRepository and includes methods for UserDocument related database operations.
 */
@Repository
public interface UserDocumentReadRepository extends JpaRepository<UserDocumentRead, Long> {
    List<UserDocumentRead> findByUserAndDocumentVersion(User user, DocumentVersion documentVersion);
    List<UserDocumentRead> findByUserAndHasRead(User user, Boolean hasRead);
}