package org.fh.documentmanagementservice.document;

import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for Document related operations.
 * This class handles the business logic for the Document entity.
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new Document.
     * It accepts a DocumentRequestDTO object and returns a DocumentResponseDTO object.
     * @param documentRequestDTO The request object containing the details of the new Document.
     * @return The DocumentResponseDTO object of the created Document.
     */
    @Transactional
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO) {
        Document document = Document.builder()
                .name(documentRequestDTO.getName())
                .filePath(documentRequestDTO.getFilePath())
                .isVisible(documentRequestDTO.isVisible())
                .isRead(documentRequestDTO.isRead())
                .categories(convertCategoryIdsToCategories(documentRequestDTO.getCategoryIds()))
                .build();
        Document savedDocument = documentRepository.save(document);
        return convertToDocumentResponseDTO(savedDocument);
    }

    /**
     * Retrieves all Documents.
     * It accepts a Pageable object and returns a Page of DocumentResponseDTO objects.
     * @param pageable The pagination information.
     * @return The Page of DocumentResponseDTO objects.
     */
    @Transactional(readOnly = true)
    public Page<DocumentResponseDTO> getAllDocuments(Pageable pageable) {
        Page<Document> documents = documentRepository.findAll(pageable);
        return documents.map(this::convertToDocumentResponseDTO);
    }


    /**
     * Retrieves a Document by its ID.
     * It accepts the ID of the Document and returns a DocumentResponseDTO object.
     * @param id The ID of the Document to retrieve.
     * @return The DocumentResponseDTO object of the retrieved Document.
     */
    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return convertToDocumentResponseDTO(document);
    }

    /**
     * Updates a Document.
     * It accepts the ID of the Document and a DocumentRequestDTO object, and returns a DocumentResponseDTO object.
     * @param id The ID of the Document to update.
     * @param documentRequestDTO The request object containing the new details of the Document.
     * @return The DocumentResponseDTO object of the updated Document.
     */
    @Transactional
    public DocumentResponseDTO updateDocument(Long id, DocumentRequestDTO documentRequestDTO) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        document.setName(documentRequestDTO.getName());
        document.setFilePath(documentRequestDTO.getFilePath());
        document.setVisible(documentRequestDTO.isVisible());
        document.setRead(documentRequestDTO.isRead());
        document.setCategories(convertCategoryIdsToCategories(documentRequestDTO.getCategoryIds()));
        Document updatedDocument = documentRepository.save(document);
        return convertToDocumentResponseDTO(updatedDocument);
    }

    /**
     * Deletes a Document.
     * It accepts the ID of the Document to delete.
     * @param id The ID of the Document to delete.
     */
    @Transactional
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    /**
     * Converts a set of category IDs to a set of Category objects.
     * It accepts a set of category IDs and returns a set of Category objects.
     * @param categoryIds The set of category IDs to convert.
     * @return The set of Category objects.
     */
    public Set<Category> convertCategoryIdsToCategories(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return Set.of();
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id)))
                .collect(Collectors.toSet());
    }

    /**
     * Converts a Document object to a DocumentResponseDTO object.
     * It accepts a Document object and returns a DocumentResponseDTO object.
     * @param document The Document object to convert.
     * @return The DocumentResponseDTO object.
     */
    private DocumentResponseDTO convertToDocumentResponseDTO(Document document) {
        Set<Long> categoryIds = document.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        return new DocumentResponseDTO(
                document.getId(),
                document.getName(),
                document.getFilePath(),
                document.isVisible(),
                document.isRead(),
                categoryIds
        );
    }
}
