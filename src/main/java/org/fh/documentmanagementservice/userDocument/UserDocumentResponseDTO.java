package org.fh.documentmanagementservice.userDocument;

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
public class UserDocumentResponseDTO {
    /**
     * The ID of the UserDocument.
     * It is used to uniquely identify the UserDocument.
     */
    private Long id;

    /**
     * The ID of the User associated with the UserDocument.
     * It is used to identify the User who is associated with the Document.
     */
    private Long userId;

    /**
     * The ID of the Document associated with the UserDocument.
     * It is used to identify the Document that is associated with the User.
     */
    private Long documentId;

    /**
     * Boolean flag indicating if the User has read the Document.
     * It is used to determine the read status of the Document by the User.
     */
    private Boolean hasRead;
}