package org.fh.documentmanagementservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
/**
 * This class represents the category update DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDTO {
    private Set<Long> groupIds;
}