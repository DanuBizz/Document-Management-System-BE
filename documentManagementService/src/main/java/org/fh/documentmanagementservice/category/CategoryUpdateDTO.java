package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDTO {
    private String name;
    private Set<Long> userIds;
}