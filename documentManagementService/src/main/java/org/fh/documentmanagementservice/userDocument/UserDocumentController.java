package org.fh.documentmanagementservice.userDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for UserDocument related operations.
 * This class handles the HTTP requests for the UserDocument entity.
 */
@RestController
@RequestMapping("/user-documents")
public class UserDocumentController {

    /**
     * Service for UserDocument related operations.
     * It is automatically injected by Spring Boot.
     */
    @Autowired
    private UserDocumentService userDocumentService;

    /**
     * Marks a Document as read by a User.
     * It accepts a UserDocumentRequestDTO object and returns a ResponseEntity with a UserDocumentResponseDTO object.
     * @param requestDTO The request object containing the details of the User and the Document.
     * @return The ResponseEntity with the UserDocumentResponseDTO object of the updated UserDocument.
     */
    @PostMapping("/mark-read")
    public ResponseEntity<UserDocumentResponseDTO> markDocumentAsRead(@RequestBody UserDocumentRequestDTO requestDTO) {
        UserDocumentResponseDTO responseDTO = userDocumentService.markDocumentAsRead(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}