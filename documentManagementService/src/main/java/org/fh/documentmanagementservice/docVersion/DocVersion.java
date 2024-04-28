package org.fh.documentmanagementservice.docVersion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.document.Document;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity class for DocVersion.
 * This class represents the DocVersion entity in the database.
 * It includes the necessary information to represent a DocVersion in the database.
 */
@Entity
@Table(name = "doc_versions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private Long id;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "is_latest", nullable = false)
    private boolean isLatest;

    @ManyToMany
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}