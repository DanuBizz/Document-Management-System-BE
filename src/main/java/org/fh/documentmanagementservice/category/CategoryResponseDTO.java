package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object (DTO) for Category responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a Category in the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    /**
     * Unique identifier for the category.
     * It is automatically generated and managed by the database.
     */
    private Long id;

    /**
     * Name of the category.
     * It is used to identify the category.
     */
    private String name;

    /**
     * Set of user IDs associated with the category.
     * It represents the users that are associated with the category.
     */
    private Set<Long> userIDs;

    /**
     * Set of document IDs associated with the category.
     * It represents the documents that are associated with the category.
     */
    private Set<Long> documentIDs;
}