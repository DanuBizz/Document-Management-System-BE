package org.fh.documentmanagementservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fh.documentmanagementservice.category.Category;

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

    private String username;
    private String email;
    private Boolean isAdmin;
    private Set<Category> categories;

}