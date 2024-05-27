package org.fh.documentmanagementservice.group;

import jakarta.persistence.*;
import lombok.*;
import org.fh.documentmanagementservice.user.User;

import java.util.HashSet;
import java.util.Set;

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
