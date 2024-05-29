package org.fh.documentmanagementservice.documentVersion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Repository interface for DocumentVersion related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on DocumentVersion entities.
 */
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    Page<DocumentVersion> findByIsLatestTrue(Pageable pageable);

    Page<DocumentVersion> findByDocumentNameAndIsLatestFalse(Pageable pageable, String documentName);

    List<DocumentVersion> findByDocumentIdAndIsLatestTrue(Long documentId);

    @Query("SELECT dv FROM DocumentVersion dv WHERE dv.isLatest = true AND LOWER(dv.document.name) LIKE LOWER(CONCAT(:name, '%'))")
    Page<DocumentVersion> findByDocumentNameStartingWithIgnoreCaseAndIsLatestTrue(@Param("name") String name, Pageable pageable);
}
