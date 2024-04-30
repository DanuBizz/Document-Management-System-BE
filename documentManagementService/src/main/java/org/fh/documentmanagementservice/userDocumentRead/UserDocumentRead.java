package org.fh.documentmanagementservice.userDocumentRead;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.fh.documentmanagementservice.documentVersion.DocumentVersion;
import org.fh.documentmanagementservice.user.User;

/**
 * Entity class for UserDocument.
 * This class represents the association between a User and a Document in the database.
 * It includes the necessary information to represent a UserDocument in the database.
 */
@Entity
@Table(name = "user_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "document_version_id", nullable = false)
    private DocumentVersion documentVersion;

    @Column(nullable = false)
    private Boolean hasRead;
}