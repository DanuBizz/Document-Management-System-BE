package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object (DTO) for Category requests.
 * This class is used to transfer data between the client and the server during HTTP requests.
 * It includes the necessary information to create or update a Category.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
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