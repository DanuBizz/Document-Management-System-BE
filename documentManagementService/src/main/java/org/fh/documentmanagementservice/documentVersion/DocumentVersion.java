package org.fh.documentmanagementservice.documentVersion;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "document_versions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Document document;

    @Column(name = "file_path", nullable = false)
    private String filepath;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToMany
    @JoinTable(
            name = "document_version_category",
            joinColumns = @JoinColumn(name = "document_version_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private Boolean isLatest;

    @Column(nullable = false)
    private Boolean isVisible;
}