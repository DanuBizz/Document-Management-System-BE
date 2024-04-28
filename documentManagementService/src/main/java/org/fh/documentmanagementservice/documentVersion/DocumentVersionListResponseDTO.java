package org.fh.documentmanagementservice.documentVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
