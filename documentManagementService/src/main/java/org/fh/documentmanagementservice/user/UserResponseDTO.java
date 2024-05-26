package org.fh.documentmanagementservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for User responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a User in the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private Boolean isAdmin;
    private List<Long> groupIds;
}