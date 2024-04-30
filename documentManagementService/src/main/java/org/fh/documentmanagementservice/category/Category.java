package org.fh.documentmanagementservice.category;

import jakarta.persistence.*;
import lombok.*;
import org.fh.documentmanagementservice.user.User;

import java.util.HashSet;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
}
