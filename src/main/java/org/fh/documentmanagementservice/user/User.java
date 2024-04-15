package org.fh.documentmanagementservice.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fh.documentmanagementservice.category.Category;
import java.util.Set;

/**
 * Entity class for User.
 * This class represents a User in the database.
 * It includes the necessary information to represent a User.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Unique identifier for the user.
     * It is automatically generated and managed by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username of the user.
     * It is unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Email of the user.
     * It is unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Boolean flag indicating if the user is an admin.
     * It cannot be null.
     */
    @Column(nullable = false)
    private Boolean isAdmin;

    /**
     * Set of categories associated with the user.
     * It represents the categories that are associated with the user.
     */
    @ManyToMany(mappedBy = "users")
    private Set<Category> categories;
}