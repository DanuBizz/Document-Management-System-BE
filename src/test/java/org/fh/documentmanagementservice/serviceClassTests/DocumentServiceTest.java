package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.category.CategoryRepository;
import org.fh.documentmanagementservice.document.*;
import org.fh.documentmanagementservice.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllDocumentsSuccessfully() {
        Document document = new Document();
        document.setCategories(new HashSet<>());
        Page<Document> documents = new PageImpl<>(List.of(document));
        when(documentRepository.findAll(any(PageRequest.class))).thenReturn(documents);

        Page<DocumentResponseDTO> responseDTOs = documentService.getAllDocuments(PageRequest.of(0, 10));

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(documentRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getDocumentByIdSuccessfully() {
        Document document = new Document();
        document.setCategories(new HashSet<>());
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));

        DocumentResponseDTO responseDTO = documentService.getDocumentById(1L);

        assertNotNull(responseDTO);
        verify(documentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createDocumentSuccessfully() {
        DocumentRequestDTO requestDTO = new DocumentRequestDTO("Test", "path/to/file", true, true, new HashSet<>());
        Document document = new Document();
        document.setCategories(new HashSet<>());
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentResponseDTO responseDTO = documentService.createDocument(requestDTO);

        assertNotNull(responseDTO);
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    public void updateDocumentSuccessfully() {
        Document document = new Document();
        DocumentRequestDTO requestDTO = new DocumentRequestDTO("Test", "path/to/file", true, true, new HashSet<>());
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentResponseDTO responseDTO = documentService.updateDocument(1L, requestDTO);

        assertNotNull(responseDTO);
        verify(documentRepository, times(1)).findById(anyLong());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    public void deleteDocumentSuccessfully() {
        doNothing().when(documentRepository).deleteById(anyLong());

        documentService.deleteDocument(1L);

        verify(documentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void convertCategoryIdsToCategoriesSuccessfully() {
        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Set<Category> categories = documentService.convertCategoryIdsToCategories(Set.of(1L));

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        verify(categoryRepository, times(1)).findById(anyLong());
    }
}