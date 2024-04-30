package org.fh.documentmanagementservice.userDocumentRead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for UserDocument requests.
 * This class represents the data that is used when making requests related to UserDocuments.
 * It includes the necessary information to identify a UserDocument and update its read status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentReadRequestDTO {
    private Long userId;
    private Long documentVersionId;
}