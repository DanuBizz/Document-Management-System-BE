package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * This class represents the category response DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<Long> groupIds;
    private List<String> groupNames;
}