package org.fh.documentmanagementservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}