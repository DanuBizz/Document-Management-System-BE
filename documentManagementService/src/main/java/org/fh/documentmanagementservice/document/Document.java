package org.fh.documentmanagementservice.document;

import jakarta.persistence.*;
import lombok.*;
import org.fh.documentmanagementservice.documentVersion.DocumentVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for Document.
 * This class represents the Document entity in the database.
 * It includes the necessary information to represent a Document in the database.
 */
@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DocumentVersion> versions = new ArrayList<>();
}
