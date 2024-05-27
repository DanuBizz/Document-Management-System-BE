package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
/**
 * Data Transfer Object (DTO) for DocumentVersion responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a DocumentVersion in the response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionResponseDTO {

    private Long id;
    private String documentName;
    private String filepath;
    private LocalDateTime timestamp;
    private Set<String> categoryNames;  // Using category names for more readable responses
    private Boolean isRead;
    private Boolean isLatest;
    private Boolean isVisible;
    private DocumentOldVersionResponseDTO[] oldVersions;
    private Boolean emailSent;
}
