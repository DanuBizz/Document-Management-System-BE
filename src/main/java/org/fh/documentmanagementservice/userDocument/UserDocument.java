package org.fh.documentmanagementservice.userDocument;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.user.User;
import jakarta.persistence.*;

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
public class UserDocument {
    /**
     * Unique identifier for the UserDocument.
     * It is automatically generated and managed by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The User associated with the UserDocument.
     * It is used to identify the User who is associated with the Document.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The Document associated with the UserDocument.
     * It is used to identify the Document that is associated with the User.
     */
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    /**
     * Boolean flag indicating if the User has read the Document.
     * It is used to determine the read status of the Document by the User.
     */
    @Column(nullable = false)
    private Boolean hasRead;
}