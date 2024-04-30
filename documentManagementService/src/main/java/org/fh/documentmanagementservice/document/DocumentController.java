package org.fh.documentmanagementservice.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;
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

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<Page<Document>> getAllDocuments(Pageable pageable) {
        Page<Document> documentPage = documentService.getAllDocuments(pageable);
        return ResponseEntity.ok(documentPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody DocumentRequestDTO documentRequestDTO) {
        Document document = documentService.createDocument(documentRequestDTO);
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody DocumentRequestDTO documentRequestDTO) {
        Document document = documentService.updateDocument(id, documentRequestDTO);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}