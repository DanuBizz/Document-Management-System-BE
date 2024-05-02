package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentOldVersionResponseDTO {
    private Long id;
    private String documentName;
    private String filepath;
    private LocalDateTime timestamp;
    private Set<String> categoryNames;  // Using category names for more readable responses
    private Boolean isRead;
    private Boolean isLatest;
    private Boolean isVisible;
}
