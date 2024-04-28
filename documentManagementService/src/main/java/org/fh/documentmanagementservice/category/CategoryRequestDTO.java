package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Category requests.
 * This class is used to transfer data between the client and the server during HTTP requests.
 * It includes the necessary information to create or update a Category.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    private String name;
}