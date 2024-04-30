package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.documentVersion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentVersionServiceTest {

    @InjectMocks
    private DocumentVersionService documentVersionService;

    @Mock
    private DocumentVersionRepository documentVersionRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MultipartFile file;

    @Mock
    private Document document;

    @Mock
    private Category category;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDocumentVersion_invalidDocumentName_throwsException() throws IOException {
        DocumentVersionRequestDTO dto = new DocumentVersionRequestDTO();
        dto.setName("Test Document");
        dto.setFile(file);
        dto.setTimestamp(LocalDateTime.now());
        Set<Long> categoryIds = new HashSet<>();
        categoryIds.add(1L);
        dto.setCategoryIds(categoryIds);

        when(documentRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> documentVersionService.createDocumentVersion(dto));
    }

    @Test
    public void createDocumentVersion_invalidCategoryId_throwsException() throws IOException {
        DocumentVersionRequestDTO dto = new DocumentVersionRequestDTO();
        dto.setName("Test Document");
        dto.setFile(file);
        dto.setTimestamp(LocalDateTime.now());
        Set<Long> categoryIds = new HashSet<>();
        categoryIds.add(1L);
        dto.setCategoryIds(categoryIds);

        when(documentRepository.findByName(anyString())).thenReturn(Optional.of(document));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> documentVersionService.createDocumentVersion(dto));
    }
}