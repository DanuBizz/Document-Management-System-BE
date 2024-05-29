package org.fh.documentmanagementservice.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * Data Transfer Object (DTO) for Group responses.
 * This class is used to transfer data between the server and the client during HTTP responses.
 * It includes the necessary information to represent a Group in the response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private String name;
    private List<Long> userIds;
    private List<String> usernames;
}

