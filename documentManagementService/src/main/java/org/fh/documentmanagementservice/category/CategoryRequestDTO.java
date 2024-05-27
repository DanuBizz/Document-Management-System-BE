package org.fh.documentmanagementservice.category;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    private String name;
    private List<String> groupNames;
}
