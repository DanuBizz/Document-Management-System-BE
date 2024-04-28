package org.fh.documentmanagementservice.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.documentVersion.DocumentVersion;

import java.util.HashSet;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private Set<DocumentVersion> versions = new HashSet<>();
}