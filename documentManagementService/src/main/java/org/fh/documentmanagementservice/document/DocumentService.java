package org.fh.documentmanagementservice.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for Document related operations.
 * This class handles the business logic for the Document entity.
 */
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Page<Document> getAllDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(DocumentRequestDTO documentRequestDTO) {
        Document document = new Document();
        document.setName(documentRequestDTO.getName());
        return documentRepository.save(document);
    }

    public Document updateDocument(Long id, DocumentRequestDTO documentRequestDTO) {
        Document document = documentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        document.setName(documentRequestDTO.getName());
        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        documentRepository.deleteById(id);
    }
}
