package org.fh.documentmanagementservice.category;

import jakarta.persistence.*;
import lombok.*;

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
}