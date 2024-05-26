package org.fh.documentmanagementservice.documentVersion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    Page<DocumentVersion> findByIsLatestTrue(Pageable pageable);

    Page<DocumentVersion> findByDocumentNameAndIsLatestFalse(Pageable pageable, String documentName);

    List<DocumentVersion> findByDocumentIdAndIsLatestTrue(Long documentId);

    Page<DocumentVersion> findByDocumentNameStartingWithIgnoreCase(String name, Pageable pageable);

}
