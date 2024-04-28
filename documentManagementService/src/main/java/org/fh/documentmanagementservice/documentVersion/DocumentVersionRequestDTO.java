package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionRequestDTO {

    private Long documentId;
    private MultipartFile file;  // File received from the frontend
    private LocalDateTime timestamp;  // Timestamp from the frontend, if any
    private Set<Long> categoryIds;  // Set of category IDs to link

}