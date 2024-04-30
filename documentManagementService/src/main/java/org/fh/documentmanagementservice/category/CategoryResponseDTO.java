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
    private Long id;
    private String name;
    private Set<String> userNames; // Changed from userIds to userNames
}