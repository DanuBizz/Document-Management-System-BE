package org.fh.documentmanagementservice.documentVersion;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/docVersions")
public class DocumentVersionController {

    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    @PostMapping
    public ResponseEntity<DocumentVersionResponseDTO> uploadDocumentVersion(@ModelAttribute DocumentVersionRequestDTO requestDTO) {
        try {
            DocumentVersionResponseDTO responseDTO = documentVersionService.createDocumentVersion(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentVersionResponseDTO> getDocumentVersion(@PathVariable Long id) {
        DocumentVersionResponseDTO responseDTO = documentVersionService.getDocumentVersion(id);
        return ResponseEntity.ok(responseDTO);
    }
}