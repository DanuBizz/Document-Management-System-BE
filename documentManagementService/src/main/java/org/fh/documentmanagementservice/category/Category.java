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

    @ElementCollection
    @CollectionTable(name = "category_groups", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "group_id")
    private Set<Long> groupIds = new HashSet<>();
}