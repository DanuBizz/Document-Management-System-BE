package org.fh.documentmanagementservice.category;

import jakarta.persistence.*;
import lombok.*;
import org.fh.documentmanagementservice.group.Group;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Group> groups = new HashSet<>();
}