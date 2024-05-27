package org.fh.documentmanagementservice.category;

import org.fh.documentmanagementservice.group.Group;
import org.fh.documentmanagementservice.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, GroupRepository groupRepository) {
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        Set<Long> groupIds = groupRepository.findAllByNameIn(categoryRequestDTO.getGroupNames())
                .stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
        category.setGroupIds(groupIds);
        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryResponseDTO(savedCategory);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setName(categoryRequestDTO.getName());
        Set<Long> groupIds = groupRepository.findAllByNameIn(categoryRequestDTO.getGroupNames())
                .stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
        category.setGroupIds(groupIds);
        Category updatedCategory = categoryRepository.save(category);
        return convertToCategoryResponseDTO(updatedCategory);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertToCategoryResponseDTO(category);
    }

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(this::convertToCategoryResponseDTO);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO convertToCategoryResponseDTO(Category category) {
        List<Long> groupIds = new ArrayList<>(category.getGroupIds());
        List<String> groupNames = groupRepository.findAllById(groupIds)
                .stream()
                .map(Group::getName)
                .collect(Collectors.toList());
        return new CategoryResponseDTO(category.getId(), category.getName(), groupIds, groupNames);
    }

    public Page<CategoryResponseDTO> searchCategories(String search, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findByNameStartingWithIgnoreCase(search, pageable);
        return categoryPage.map(this::convertToCategoryResponseDTO);
    }
}