package org.fh.documentmanagementservice.category;

import jakarta.persistence.*;
import lombok.*;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.user.User;
import java.util.Set;

/**
 * Represents a Category entity in the database.
 * This class is a part of the document management service.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    /**
     * Unique identifier for the category.
     * It is automatically generated and managed by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the category.
     * It is a non-nullable field and must be unique across all categories.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Set of users associated with the category.
     * It represents a many-to-many relationship between User and Category.
     */
    @ManyToMany
    private Set<User> users;

    /**
     * Set of documents associated with the category.
     * It represents a many-to-many relationship between Document and Category.
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Document> documents;
}