package org.fh.documentmanagementservice.userDocumentRead;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-documents")
public class UserDocumentReadController {

    private final UserDocumentReadService userDocumentReadService;

    public UserDocumentReadController(UserDocumentReadService userDocumentReadService) {
        this.userDocumentReadService = userDocumentReadService;
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<UserDocumentReadResponseDTO> markDocumentAsRead(@RequestBody UserDocumentReadRequestDTO request) {
        UserDocumentReadResponseDTO response = userDocumentReadService.markDocumentAsRead(request);
        return ResponseEntity.ok(response);
    }
}