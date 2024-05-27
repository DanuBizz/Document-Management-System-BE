package org.fh.documentmanagementservice.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

