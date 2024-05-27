package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;
/**
 * Data Transfer Object (DTO) for DocumentVersion requests.
 * This class is used to transfer data between the server and the client during HTTP requests.
 * It includes the necessary information to represent a DocumentVersion in the request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionRequestDTO {

    private String name;
    private MultipartFile file;  // File received from the frontend
    private LocalDateTime timestamp;  // Timestamp from the frontend, if any
    private Set<Long> categoryIds;  // Set of category IDs to link

}