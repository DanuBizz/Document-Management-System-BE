package org.fh.documentmanagementservice.userDocument;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.docVersion.DocVersion;
import org.fh.documentmanagementservice.user.User;

import java.util.Set;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;


    @ManyToOne
    @JoinColumn(name = "doc_version_id")
    private DocVersion docVersion;


    @ManyToMany
    @JoinTable(
            name = "user_document_categories",
            joinColumns = @JoinColumn(name = "user_document_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;


    @Column(nullable = false)
    private Boolean hasRead;
}