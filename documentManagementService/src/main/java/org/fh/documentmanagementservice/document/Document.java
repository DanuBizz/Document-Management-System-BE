package org.fh.documentmanagementservice.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.category.Category;

import java.util.Set;

/**
 * Entity class for Document.
 * This class represents the Document entity in the database.
 * It includes the necessary information to represent a Document in the database.
 */
@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    /**
     * Unique identifier for the document.
     * It is automatically generated and managed by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    /**
     * Name of the document.
     * It is used to identify the document.
     */
    @Column(nullable = false)
    private String name;

    /**
     * File path of the document.
     * It is used to locate the document in the file system.
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    /**
     * Boolean flag indicating if the document is visible.
     * It is used to determine the visibility of the document.
     */
    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    /**
     * Boolean flag indicating if the document is read.
     * It is used to determine the read status of the document.
     */
    @Column(nullable = false)
    private boolean isRead;

    /**
     * Set of categories associated with the document.
     * It represents the categories that are associated with the document.
     */
    @ManyToMany
    private Set<Category> categories;
}