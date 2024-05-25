package org.fh.documentmanagementservice.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRequestDTO {
    private String name;
    private List<String> usernames;
}