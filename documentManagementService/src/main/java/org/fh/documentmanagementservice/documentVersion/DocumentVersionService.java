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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentVersionService {

    private static final String FILE_DIRECTORY = "C:\\Users\\Hasnat\\Downloads\\KBB-test\\";

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DocumentService documentService;

    public Page<DocumentVersionResponseDTO> getAllDocumentVersionsDTO(Pageable pageable) {
        return getAllDocumentVersions(pageable).map(this::convertToResponseDTO);
    }

    public Page<DocumentVersion> getAllDocumentVersions(Pageable pageable) {
        return documentVersionRepository.findAll(pageable);
    }

    public Page<DocumentVersionResponseDTO> getLatestWithAssociatedVersionsDTO(Pageable pageable) {
        Page<DocumentVersion> documentVersionPage = getLatestDocumentVersions(pageable);
        Page<DocumentVersionResponseDTO> dtoPage = documentVersionPage.map(this::convertToResponseDTO);

        dtoPage.forEach(dto -> {
            List<DocumentOldVersionResponseDTO> oldVersionDTOList = getNonLatestDocumentVersionsDTO(dto.getDocumentName());
            dto.setOldVersions(oldVersionDTOList.toArray(new DocumentOldVersionResponseDTO[0]));
        });

        return dtoPage;
    }

    public Page<DocumentVersion> getLatestDocumentVersions(Pageable pageable) {
        return documentVersionRepository.findByIsLatestTrue(pageable);
    }

    public List<DocumentOldVersionResponseDTO> getNonLatestDocumentVersionsDTO(String documentName) {
        return getNonLatestDocumentVersions(documentName).map(this::convertToOldResponseDTO).getContent();
    }

    public Page<DocumentVersion> getNonLatestDocumentVersions(String documentName) {
        Pageable nonLatestPageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "timestamp"));
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

        return DocumentVersionResponseDTO.builder()
                .id(documentVersion.getId())
                .documentName(documentVersion.getDocument().getName())
                .filepath(documentVersion.getFilepath())
                .timestamp(documentVersion.getTimestamp())
                .categoryNames(categoryNames)
                .isRead(documentVersion.getIsRead())
                .isLatest(documentVersion.getIsLatest())
                .isVisible(documentVersion.getIsVisible())
                .oldVersions(new DocumentOldVersionResponseDTO[0])
                .build();
    }

    public DocumentOldVersionResponseDTO convertToOldResponseDTO(DocumentVersion documentVersion) {
        Set<String> categoryNames = documentVersion.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        return DocumentOldVersionResponseDTO.builder()
                .id(documentVersion.getId())
                .documentName(documentVersion.getDocument().getName())
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
