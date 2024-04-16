package org.fh.documentmanagementservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for User requests.
 * This class is used to transfer data between the server and the client during HTTP requests.
 * It includes the necessary information to represent a User in the request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    /**
     * Username of the user.
     * It is used to identify the user.
     */
    private String username;

    /**
     * Email of the user.
     * It is used for communication with the user.
     */
    private String email;

    /**
     * Boolean flag indicating if the user is an admin.
     * It is used to determine the user's role.
     */
    private Boolean isAdmin;

    /**
     * Set of category IDs associated with the user.
     * It represents the categories that are associated with the user.
     */
    private Set<Long> categoryIds;
}