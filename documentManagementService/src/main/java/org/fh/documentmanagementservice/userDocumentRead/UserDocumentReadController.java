package org.fh.documentmanagementservice.userDocumentRead;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller for UserDocumentRead related operations.
 * It handles the HTTP requests and responses for the UserDocumentRead entity.
 */
@RestController
@RequestMapping("/user-documents")
public class UserDocumentReadController {

    private final UserDocumentReadService userDocumentReadService;

    public UserDocumentReadController(UserDocumentReadService userDocumentReadService) {
        this.userDocumentReadService = userDocumentReadService;
    }
    /**
     * Mark a document as read by a user.
     * @param request The UserDocumentReadRequestDTO object containing the UserDocumentRead data.
     * @return The UserDocumentReadResponseDTO object containing the updated UserDocumentRead data.
     */
    @PostMapping("/mark-as-read")
    public ResponseEntity<UserDocumentReadResponseDTO> markDocumentAsRead(@RequestBody UserDocumentReadRequestDTO request) {
        UserDocumentReadResponseDTO response = userDocumentReadService.markDocumentAsRead(request);
        return ResponseEntity.ok(response);
    }
}