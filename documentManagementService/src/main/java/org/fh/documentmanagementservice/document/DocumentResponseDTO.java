package org.fh.documentmanagementservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object (DTO) for Document responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a Document in the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {
    /**
     * Unique identifier for the document.
     * It is automatically generated and managed by the database.
     */
    private Long id;

    /**
     * Name of the document.
     * It is used to identify the document.
     */
    private String name;

    /**
     * File path of the document.
     * It is used to locate the document in the file system.
     */
    private String filePath;

    /**
     * Boolean flag indicating if the document is visible.
     * It is used to determine the visibility of the document.
     */
    private boolean isVisible;

    /**
     * Boolean flag indicating if the document is read.
     * It is used to determine the read status of the document.
     */
    private boolean isRead;

    /**
     * Set of category IDs associated with the document.
     * It represents the categories that are associated with the document.
     */
    private Set<Long> categoryIds;
}