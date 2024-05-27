package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) for DocumentVersionList responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a DocumentVersion in the response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionListResponseDTO {
    private Long id;
    private String documentName;
    private String filepath;
    private String timestamp;
    private Boolean isRead;
    private Boolean isLatest;
    private Boolean isVisible;
}
