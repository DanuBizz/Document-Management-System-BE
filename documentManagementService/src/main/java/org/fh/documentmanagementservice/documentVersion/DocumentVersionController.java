package org.fh.documentmanagementservice.documentVersion;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/documentVersions")
public class DocumentVersionController {

    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    @GetMapping
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getAllDocumentVersions(Pageable pageable) {
        return ResponseEntity.ok(documentVersionService.getAllDocumentVersionsDTO(pageable));
    }

    @GetMapping("/latest-with-associated-versions")
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getLatestDocumentVersions(Pageable pageable) {
        return ResponseEntity.ok(documentVersionService.getLatestWithAssociatedVersionsDTO(pageable));
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<DocumentVersionResponseDTO> getDocumentVersion(@PathVariable Long id) {
        return ResponseEntity.ok(documentVersionService.getDocumentVersion(id));
    }

    @PutMapping("/{id}/toggle-visibility")
    public ResponseEntity<DocumentVersionResponseDTO> toggleDocumentVersionVisibility(@PathVariable Long id) {
        return ResponseEntity.ok(documentVersionService.toggleVisibility(id));
    }

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
