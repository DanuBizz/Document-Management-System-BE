package org.fh.documentmanagementservice.documentVersion;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documentVersions")
public class DocumentVersionController {


    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }


    @GetMapping
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getAllDocumentVersions(Pageable pageable) {
        Page<DocumentVersion> documentVersionPage = documentVersionService.getAllDocumentVersions(pageable);
        Page<DocumentVersionResponseDTO> dtoPage = documentVersionPage.map(documentVersionService::convertToResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/latest-with-associated-versions")
    public ResponseEntity<Page<DocumentVersionResponseDTO>> getLatestDocumentVersions(Pageable pageable) {

        // Get the latest version of the documents
        Page<DocumentVersion> documentVersionPage = documentVersionService.getLatestDocumentVersions(pageable);
        Page<DocumentVersionResponseDTO> dtoPage = documentVersionPage.map(documentVersionService::convertToResponseDTO);

        // Create a list to store old version DTOs
        List<DocumentOldVersionResponseDTO> oldVersionDTOs = new ArrayList<>();

        // Iterate through the latest version DTOs to fetch and add old versions
        for (DocumentVersionResponseDTO dto : dtoPage.getContent()) {
            // Fetch non-latest versions for each document
            Page<DocumentVersion> nonLatestVersionsPage = documentVersionService.getNonLatestDocumentVersions(dto.getDocumentName());
            // Convert non-latest versions to DTOs
            List<DocumentOldVersionResponseDTO> oldVersionDTOList = nonLatestVersionsPage
                    .map(documentVersionService::convertToOldResponseDTO)
                    .getContent();
            // Add old version DTOs to the list
            oldVersionDTOs.addAll(oldVersionDTOList);
            // Set the old versions array for the current latest version DTO
            dto.setOldVersions(oldVersionDTOList.toArray(new DocumentOldVersionResponseDTO[0]));
        }

        return ResponseEntity.ok(dtoPage);
    }

/*

    @GetMapping("/non-latest-versions/{documentName}")
    public ResponseEntity<Page<DocumentOldVersionResponseDTO>> getNonLatestDocumentVersions(Pageable pageable ,@PathVariable String documentName) {
        Page<DocumentVersion> documentVersionPage = documentVersionService.getNonLatestDocumentVersions(documentName, pageable);
        Page<DocumentOldVersionResponseDTO> dtoPage = documentVersionPage.map(documentVersionService::convertToOldResponseDTO);

        return ResponseEntity.ok(dtoPage);
    }
*/

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

    @PutMapping("/{id}/toggle-visibility")
    public ResponseEntity<DocumentVersionResponseDTO> toggleDocumentVersionVisibility(@PathVariable Long id) {
        DocumentVersionResponseDTO responseDTO = documentVersionService.toggleVisibility(id);
        return ResponseEntity.ok(responseDTO);
    }
}
