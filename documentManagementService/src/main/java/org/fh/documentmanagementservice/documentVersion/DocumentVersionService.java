package org.fh.documentmanagementservice.documentVersion;

import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public DocumentVersionResponseDTO createDocumentVersion(DocumentVersionRequestDTO dto) throws IOException {
        Document document = documentRepository.findById(dto.getDocumentId())
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

    private String storeFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // Generate a unique filename in case of non-unique file names uploaded
            String originalFilename = file.getOriginalFilename();
            String storedFilename = UUID.randomUUID() + "-" + originalFilename;
            // Adjusted path for file storage
            String FILE_DIRECTORY = "C:\\Users\\Hasnat\\Downloads\\KBB-test\\";
            Path destinationFilePath = Paths.get(FILE_DIRECTORY + storedFilename);
            file.transferTo(destinationFilePath);
            return destinationFilePath.toString();
        } else {
            throw new IllegalArgumentException("File is empty and cannot be saved.");
        }
    }

    private Set<Category> collectCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID")))
                .collect(Collectors.toSet());
    }

    private void updateLatestVersionFlag(Document document) {
        document.getVersions().forEach(v -> {
            v.setIsLatest(false);
            documentVersionRepository.save(v);
        });
    }

    private DocumentVersionResponseDTO convertToResponseDTO(DocumentVersion documentVersion) {
        Set<String> categoryNames = documentVersion.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        return DocumentVersionResponseDTO.builder()
                .id(documentVersion.getId())
                .documentId(documentVersion.getDocument().getId())
                .filepath(documentVersion.getFilepath())
                .timestamp(documentVersion.getTimestamp())
                .categoryNames(categoryNames)
                .isRead(documentVersion.getIsRead())
                .isLatest(documentVersion.getIsLatest())
                .isVisible(documentVersion.getIsVisible())
                .build();
    }

    public DocumentVersionResponseDTO getDocumentVersion(Long id) {
        DocumentVersion documentVersion = documentVersionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document version ID"));

        return convertToResponseDTO(documentVersion);
    }
}


