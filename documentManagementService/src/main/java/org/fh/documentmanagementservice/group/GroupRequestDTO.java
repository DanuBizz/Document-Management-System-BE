package org.fh.documentmanagementservice.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Data Transfer Object (DTO) for Group requests.
 * This class is used to transfer data between the server and the client during HTTP requests.
 * It includes the necessary information to represent a Group in the request.
 */
@Getter
@Setter
public class GroupRequestDTO {
    private String name;
    private List<String> usernames;
}