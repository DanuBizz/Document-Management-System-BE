package org.fh.documentmanagementservice.docVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.category.Category;
import org.fh.documentmanagementservice.document.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocVersionResponseDTO {

    private Long id;
    private String filePath;
    private boolean isVisible;
    private boolean isRead;
    private boolean isLatest;
    private Set<Category> categories;
    private Document document;
    private LocalDateTime timestamp;
}