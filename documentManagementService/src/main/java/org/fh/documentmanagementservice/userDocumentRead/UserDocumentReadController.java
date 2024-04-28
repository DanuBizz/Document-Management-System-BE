package org.fh.documentmanagementservice.userDocumentRead;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-documents")
public class UserDocumentReadController {

    private final UserDocumentService userDocumentService;

    public UserDocumentReadController(UserDocumentService userDocumentService) {
        this.userDocumentService = userDocumentService;
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<UserDocumentReadResponseDTO> markDocumentAsRead(@RequestBody UserDocumentReadRequestDTO request) {
        UserDocumentReadResponseDTO response = userDocumentService.markDocumentAsRead(request);
        return ResponseEntity.ok(response);
    }
}