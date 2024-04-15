package org.fh.documentmanagementservice.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for Document related operations.
 * This class handles the HTTP requests for the Document entity.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    /**
     * Service for Document related operations.
     * It is automatically injected by Spring Boot.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * Creates a new Document.
     * It accepts a DocumentRequestDTO object and returns a ResponseEntity with the created DocumentResponseDTO object and HTTP status.
     * @param documentRequestDTO The request object containing the details of the new Document.
     * @return The ResponseEntity with the created DocumentResponseDTO object and HTTP status.
     */
    @PostMapping
    public ResponseEntity<DocumentResponseDTO> createDocument(@RequestBody DocumentRequestDTO documentRequestDTO) {
        DocumentResponseDTO createdDocument = documentService.createDocument(documentRequestDTO);
        return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
    }

    /**
     * Retrieves all Documents.
     * It accepts a Pageable object and returns a ResponseEntity with a Page of DocumentResponseDTO objects and HTTP status.
     * @param pageable The pagination information.
     * @return The ResponseEntity with a Page of DocumentResponseDTO objects and HTTP status.
     */
    @GetMapping
    public ResponseEntity<Page<DocumentResponseDTO>> getAllDocuments(Pageable pageable) {
        Page<DocumentResponseDTO> documents = documentService.getAllDocuments(pageable);
        return ResponseEntity.ok(documents);
    }

    /**
     * Retrieves a Document by its ID.
     * It accepts the ID of the Document and returns a ResponseEntity with the DocumentResponseDTO object and HTTP status.
     * @param id The ID of the Document to retrieve.
     * @return The ResponseEntity with the DocumentResponseDTO object and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> getDocument(@PathVariable Long id) {
        DocumentResponseDTO document = documentService.getDocumentById(id);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    /**
     * Updates a Document.
     * It accepts the ID of the Document and a DocumentRequestDTO object, and returns a ResponseEntity with the updated DocumentResponseDTO object and HTTP status.
     * @param id The ID of the Document to update.
     * @param documentRequestDTO The request object containing the new details of the Document.
     * @return The ResponseEntity with the updated DocumentResponseDTO object and HTTP status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable Long id, @RequestBody DocumentRequestDTO documentRequestDTO) {
        DocumentResponseDTO updatedDocument = documentService.updateDocument(id, documentRequestDTO);
        return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
    }

    /**
     * Deletes a Document.
     * It accepts the ID of the Document to delete and returns a ResponseEntity with HTTP status.
     * @param id The ID of the Document to delete.
     * @return The ResponseEntity with HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}