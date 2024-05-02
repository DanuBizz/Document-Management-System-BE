package org.fh.documentmanagementservice.documentVersion;

import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentVersionService {

    private static final String FILE_DIRECTORY = "C:\\Users\\mike\\KBB-test";

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DocumentService documentService;


    public Page<DocumentVersion> getAllDocumentVersions(Pageable pageable) {
        return documentVersionRepository.findAll(pageable);
    }

    public Page<DocumentVersion> getLatestDocumentVersions(Pageable pageable) {
        return documentVersionRepository.findByIsLatestTrue(pageable);


    }

    public Page<DocumentVersion> getNonLatestDocumentVersions(String documentName) {
        // Create a pageable object for fetching non-latest versions
        Pageable nonLatestPageable = PageRequest.of(0, Integer.MAX_VALUE);
        Sort sort = Sort.by(Sort.Direction.DESC, "timestamp"); // Sortierung nach timestamp (absteigend)
        nonLatestPageable = PageRequest.of(nonLatestPageable.getPageNumber(), nonLatestPageable.getPageSize(), sort);

        return documentVersionRepository.findByDocumentNameAndIsLatestFalse(nonLatestPageable, documentName);
    }


    public DocumentVersionResponseDTO getDocumentVersion(Long id) {
        DocumentVersion documentVersion = documentVersionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document version ID"));

        return convertToResponseDTO(documentVersion);
    }


    public DocumentVersionResponseDTO createDocumentVersion(DocumentVersionRequestDTO dto) throws IOException {
        Document document = documentRepository.findByName(dto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID"));

        String storedFilePath = storeFile(dto.getFile());

        DocumentVersion documentVersion = DocumentVersion.builder()
                .document(document)
                .filepath(storedFilePath)
                .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now())
                .categories(collectCategories(dto.getCategoryIds()))
                .isRead(false)
                .isLatest(true)
                .isVisible(true)
                .build();

        updateLatestVersionFlag(document);

        return convertToResponseDTO(documentVersionRepository.save(documentVersion));
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // Generate a unique filename in case of non-unique file names uploaded
            String originalFilename = file.getOriginalFilename();
            String storedFilename = UUID.randomUUID() + "-" + originalFilename;
            Path destinationFilePath = Paths.get(FILE_DIRECTORY + storedFilename);
            file.transferTo(destinationFilePath);
            return destinationFilePath.toString();
        } else {
            throw new IllegalArgumentException("File is empty and cannot be saved.");
        }
    }

    public Set<Category> collectCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID")))
                .collect(Collectors.toSet());
    }

    public void updateLatestVersionFlag(Document document) {
        document.getVersions().forEach(v -> {
            v.setIsLatest(false);
            documentVersionRepository.save(v);
        });
    }

    public DocumentVersionResponseDTO convertToResponseDTO(DocumentVersion documentVersion) {
        Set<String> categoryNames = documentVersion.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        String documentName = documentVersion.getDocument().getName();
        DocumentOldVersionResponseDTO[] emptyIntArray = new DocumentOldVersionResponseDTO[0];
        return DocumentVersionResponseDTO.builder()
                .id(documentVersion.getId())
                .documentName(documentName)
                .filepath(documentVersion.getFilepath())
                .timestamp(documentVersion.getTimestamp())
                .categoryNames(categoryNames)
                .isRead(documentVersion.getIsRead())
                .isLatest(documentVersion.getIsLatest())
                .isVisible(documentVersion.getIsVisible())
                .oldVersions(emptyIntArray)
                .build();
    }

    public DocumentOldVersionResponseDTO convertToOldResponseDTO(DocumentVersion documentVersion) {
        Set<String> categoryNames = documentVersion.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        String documentName = documentVersion.getDocument().getName();
        DocumentOldVersionResponseDTO[] emptyIntArray = new DocumentOldVersionResponseDTO[0];
        return DocumentOldVersionResponseDTO.builder()
                .id(documentVersion.getId())
                .documentName(documentName)
                .filepath(documentVersion.getFilepath())
                .timestamp(documentVersion.getTimestamp())
                .categoryNames(categoryNames)
                .isRead(documentVersion.getIsRead())
                .isLatest(documentVersion.getIsLatest())
                .isVisible(documentVersion.getIsVisible())
                .build();
    }

    public DocumentVersionResponseDTO toggleVisibility(Long id) {
        DocumentVersion documentVersion = documentVersionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document version ID"));

        documentVersion.setIsVisible(!documentVersion.getIsVisible());
        return convertToResponseDTO(documentVersionRepository.save(documentVersion));
    }
}


