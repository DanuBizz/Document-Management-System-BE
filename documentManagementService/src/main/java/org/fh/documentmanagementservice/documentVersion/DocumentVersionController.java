package org.fh.documentmanagementservice.documentVersion;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
/**
 * Controller class for DocumentVersion related operations.
 * This class handles the HTTP requests for the DocumentVersion entity.
 */
@RestController
@RequestMapping("/documentVersions")
public class DocumentVersionController {

    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }
    /**
     * Get all document versions.
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getAllDocumentVersions(Pageable pageable) {
        return ResponseEntity.ok(documentVersionService.getAllDocumentVersionsDTO(pageable));
    }
    /**
     * Get all document versions with their associated versions.
     * @param search
     * @param pageable
     * @return
     */
    @GetMapping("/latest-with-associated-versions")
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getLatestDocumentVersions(@RequestParam(defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.ok(documentVersionService.getLatestWithAssociatedVersionsDTO(search, pageable));
    }
    /**
     * Upload a document version.
     * @param requestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<DocumentVersionResponseDTO> uploadDocumentVersion(@ModelAttribute DocumentVersionRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(documentVersionService.createDocumentVersion(requestDTO));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    /**
     * Get a document version by ID.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentVersionResponseDTO> getDocumentVersion(@PathVariable Long id) {
        return ResponseEntity.ok(documentVersionService.getDocumentVersion(id));
    }
    /**
     * Update a document version.
     * @param id
     * @return
     */
    @PutMapping("/{id}/toggle-visibility")
    public ResponseEntity<DocumentVersionResponseDTO> toggleDocumentVersionVisibility(@PathVariable Long id) {
        return ResponseEntity.ok(documentVersionService.toggleVisibility(id));
    }
    /**
     * Delete a document version.
     * @param id
     * @return
     */
    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        Resource file = documentVersionService.getFileAsResource(id);
        if (file.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
