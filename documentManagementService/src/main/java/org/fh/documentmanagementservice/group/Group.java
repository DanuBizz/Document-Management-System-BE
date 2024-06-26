package org.fh.documentmanagementservice.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
/**
 * Entity class for Group.
 * This class represents the Group entity in the database.
 * It includes the necessary information to represent a Group in the database.
 */
@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name = "group_users", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "user_id")
    private Set<Long> userIds = new HashSet<>();
}
