package org.fh.documentmanagementservice.userDocument;

import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for UserDocument related operations.
 * This class handles the business logic for the UserDocument entity.
 */
@Service
public class UserDocumentService {


    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Marks a Document as read by a User.
     * It accepts a UserDocumentRequestDTO object and returns a UserDocumentResponseDTO object.
     * This method is transactional, meaning that it ensures the consistency of the database during its execution.
     * @param requestDTO The request object containing the details of the User and the Document.
     * @return The UserDocumentResponseDTO object of the updated UserDocument.
     * @throws RuntimeException If the User or the Document is not found.
     */
    @Transactional
    public UserDocumentResponseDTO markDocumentAsRead(UserDocumentRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDTO.getUserId()));
        Document document = documentRepository.findById(requestDTO.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + requestDTO.getDocumentId()));

        UserDocument userDocument = userDocumentRepository.findByUserIdAndDocumentId(user.getId(), document.getId())
                .orElse(new UserDocument());

        userDocument.setUser(user);
        userDocument.setDocument(document);
        userDocument.setHasRead(requestDTO.getHasRead());

        UserDocument savedUserDocument = userDocumentRepository.save(userDocument);
        return new UserDocumentResponseDTO(savedUserDocument.getId(), savedUserDocument.getUser().getId(),
                savedUserDocument.getDocument().getId(), savedUserDocument.getHasRead());
    }

}