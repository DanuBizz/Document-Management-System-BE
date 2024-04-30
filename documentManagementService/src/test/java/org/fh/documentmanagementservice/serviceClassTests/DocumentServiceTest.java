package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.document.*;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllDocuments_returnsPageOfDocuments() {
        Page<Document> documentPage = mock(Page.class);
        when(documentRepository.findAll(any(Pageable.class))).thenReturn(documentPage);

        assertEquals(documentPage, documentService.getAllDocuments(mock(Pageable.class)));
    }

    @Test
    public void getDocumentById_existingId_returnsDocument() {
        Document document = new Document();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        assertEquals(document, documentService.getDocumentById(1L).get());
    }

    @Test
    public void createDocument_validRequest_returnsDocument() {
        DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();
        documentRequestDTO.setName("Test");

        Document document = new Document();
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        assertEquals(document, documentService.createDocument(documentRequestDTO));
    }

    @Test
    public void updateDocument_existingId_returnsUpdatedDocument() {
        DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();
        documentRequestDTO.setName("Test");

        Document document = new Document();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        assertEquals(document, documentService.updateDocument(1L, documentRequestDTO));
    }

    @Test
    public void updateDocument_nonExistingId_throwsException() {
        DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();
        documentRequestDTO.setName("Test");

        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> documentService.updateDocument(1L, documentRequestDTO));
    }

    @Test
    public void deleteDocument_existingId_noExceptionThrown() {
        when(documentRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> documentService.deleteDocument(1L));
    }

    @Test
    public void deleteDocument_nonExistingId_throwsException() {
        when(documentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> documentService.deleteDocument(1L));
    }
}