package org.fh.documentmanagementservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.documentVersion.DocumentVersion;

import java.util.HashSet;
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

    private Long id;
    private String name;
    private Set<DocumentVersion> documentVersions= new HashSet<>();

}