package org.fh.documentmanagementservice.documentVersion;

import jakarta.servlet.http.HttpServletResponse;
import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.category.CategoryRepository;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.document.DocumentService;
import org.fh.documentmanagementservice.email.EmailService;
import org.fh.documentmanagementservice.group.GroupRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DocumentVersionService {

    @Value("${spring.application.file-directory}")
    private String fileDirectory;

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(DocumentVersionService.class.getName());

    public Page<DocumentVersionResponseDTO> getAllDocumentVersionsDTO(Pageable pageable) {
        return documentVersionRepository.findAll(pageable).map(this::convertToResponseDTO);
    }

    public Page<DocumentVersionResponseDTO> getLatestWithAssociatedVersionsDTO(Pageable pageable) {
        return documentVersionRepository.findByIsLatestTrue(pageable)
                .map(this::convertToResponseDTO)
                .map(dto -> {
                    dto.setOldVersions(getNonLatestDocumentVersionsDTO(dto.getDocumentName()).toArray(new DocumentOldVersionResponseDTO[0]));
                    return dto;
                });
    }

    public DocumentVersionResponseDTO getDocumentVersion(Long id) {
        DocumentVersion documentVersion = documentVersionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document version ID"));
        return convertToResponseDTO(documentVersion);
    }

    public DocumentVersionResponseDTO createDocumentVersion(DocumentVersionRequestDTO dto) throws IOException {
        Document document = documentRepository.findByName(dto.getName())
                .orElseGet(() -> {
                    Document newDocument = new Document();
                    newDocument.setName(dto.getName());
                    return documentRepository.save(newDocument);
                });

        updateLatestVersionFlag(document);

        String storedFilePath = storeFile(dto.getFile(), dto.getName());

        DocumentVersion documentVersion = DocumentVersion.builder()
                .document(document)
                .filepath(storedFilePath)
                .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now())
                .categories(collectCategories(dto.getCategoryIds()))
                .isRead(false)
                .isLatest(true)
                .isVisible(true)
                .build();

        document.getVersions().add(documentVersion);

        try {
            DocumentVersion savedDocumentVersion = documentVersionRepository.save(documentVersion);
            boolean emailSent = sendEmailNotifications(documentVersion);
            DocumentVersionResponseDTO responseDTO = convertToResponseDTO(savedDocumentVersion);
            responseDTO.setEmailSent(emailSent);
            return responseDTO;
        } catch (Exception e) {
            logger.info("Error saving DocumentVersion: ");
            throw e;
        }
    }

    private boolean sendEmailNotifications(DocumentVersion documentVersion) {
        Set<Long> groupIds = documentVersion.getCategories().stream()
                .flatMap(category -> category.getGroupIds().stream())
                .collect(Collectors.toSet());

        Set<Long> userIds = groupRepository.findAllById(groupIds).stream()
                .flatMap(group -> group.getUserIds().stream())
                .collect(Collectors.toSet());

        List<User> users = userRepository.findAllById(userIds);
        boolean allEmailsSent = true;

        for (User user : users) {
            boolean emailSent = emailService.sendDocumentNotification(user.getUsername(), user.getEmail(), documentVersion.getDocument().getName());
            if (!emailSent) {
                allEmailsSent = false;
            }
        }

        return allEmailsSent;
    }

    public String storeFile(MultipartFile file, String documentName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty and cannot be saved.");
        }

        String documentPath = fileDirectory + documentName;
        File directory = new File(documentPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String storedFilename = UUID.randomUUID() + "-" + originalFilename;
        Path destinationFilePath = Paths.get(documentPath, storedFilename);
        file.transferTo(destinationFilePath);
        return destinationFilePath.toString();
    }

    public Set<Category> collectCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID")))
                .collect(Collectors.toSet());
    }

    public void updateLatestVersionFlag(Document document) {
        List<DocumentVersion> latestVersions = documentVersionRepository.findByDocumentIdAndIsLatestTrue(document.getId());
        for (DocumentVersion version : latestVersions) {
            version.setIsLatest(false);
            documentVersionRepository.save(version);
        }
    }

    public DocumentVersionResponseDTO toggleVisibility(Long id) {
        DocumentVersion documentVersion = documentVersionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document version ID"));
        documentVersion.setIsVisible(!documentVersion.getIsVisible());
        return convertToResponseDTO(documentVersionRepository.save(documentVersion));
    }

    public void writeFileToResponse(Long id, HttpServletResponse response) {
        DocumentVersionResponseDTO documentVersion = getDocumentVersion(id);
        Path filePath = Paths.get(documentVersion.getFilepath());

        if (Files.notExists(filePath)) {
            throw new RuntimeException("File not found with filename: " + documentVersion.getFilepath());
        }

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + filePath.getFileName().toString());

        try {
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing file to output stream.");
        }
    }

    private DocumentVersionResponseDTO convertToResponseDTO(DocumentVersion documentVersion) {
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

    private DocumentOldVersionResponseDTO convertToOldResponseDTO(DocumentVersion documentVersion) {
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

    private List<DocumentOldVersionResponseDTO> getNonLatestDocumentVersionsDTO(String documentName) {
        return documentVersionRepository.findByDocumentNameAndIsLatestFalse(PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "timestamp")), documentName)
                .stream()
                .map(this::convertToOldResponseDTO)
                .collect(Collectors.toList());
    }

    public Resource getFileAsResource(Long id) {
        DocumentVersionResponseDTO documentVersion = getDocumentVersion(id);
        Path filePath = Paths.get(documentVersion.getFilepath());

        if (Files.notExists(filePath)) {
            throw new RuntimeException("File not found with filename: " + documentVersion.getFilepath());
        }

        return new FileSystemResource(filePath.toFile());
    }

    public Page<DocumentVersion> searchDocumentVersions(String search, Pageable pageable) {
        return documentVersionRepository.findByDocumentNameStartingWithIgnoreCase(search, pageable);
    }
}