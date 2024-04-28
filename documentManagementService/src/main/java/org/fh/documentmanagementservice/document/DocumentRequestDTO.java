package org.fh.documentmanagementservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for Document requests.
 * This class is used to transfer data between the server and the client during HTTP requests.
 * It includes the necessary information to represent a Document in the request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDTO {
    private String name;
}