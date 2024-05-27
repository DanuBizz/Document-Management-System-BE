package org.fh.documentmanagementservice.userDocumentRead;

import jakarta.transaction.Transactional;
import org.fh.documentmanagementservice.documentVersion.DocumentVersion;
import org.fh.documentmanagementservice.documentVersion.DocumentVersionRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Service class for UserDocument related operations.
 * This class handles the business logic for the UserDocument entity.
 */
@Service
public class UserDocumentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Autowired
    private UserDocumentReadRepository userDocumentReadRepository;

    /**
     * Mark a document as read.
     * @param request
     * @return
     */
    @Transactional
    public UserDocumentReadResponseDTO markDocumentAsRead(UserDocumentReadRequestDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        DocumentVersion documentVersion = documentVersionRepository.findById(request.getDocumentVersionId()).orElseThrow(() -> new RuntimeException("Document version not found"));

        UserDocumentRead userDocumentRead = new UserDocumentRead();
        userDocumentRead.setUser(user);
        userDocumentRead.setDocumentVersion(documentVersion);
        userDocumentRead.setHasRead(true);

        userDocumentReadRepository.save(userDocumentRead);

        return new UserDocumentReadResponseDTO(user.getId(), documentVersion.getId(), true);
    }
}