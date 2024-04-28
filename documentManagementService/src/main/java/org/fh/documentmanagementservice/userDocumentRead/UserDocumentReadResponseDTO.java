package org.fh.documentmanagementservice.userDocumentRead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for UserDocument responses.
 * This class represents the data that is returned when making requests related to UserDocuments.
 * It includes the necessary information to identify a UserDocument and its read status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentReadResponseDTO {
    private Long userId;
    private Long documentVersionId;
    private Boolean readStatus;
}