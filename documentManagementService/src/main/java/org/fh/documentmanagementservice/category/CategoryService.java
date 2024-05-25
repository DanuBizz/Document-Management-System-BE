package org.fh.documentmanagementservice.category;

import org.fh.documentmanagementservice.group.Group;
import org.fh.documentmanagementservice.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, GroupRepository groupRepository) {
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        Set<Group> groups = new HashSet<>(groupRepository.findAllById(categoryRequestDTO.getGroupIds()));
        category.setGroups(groups);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category category = getCategoryById(id)
                .orElseThrow(ResourceNotFoundException::new);
        category.getGroups().clear();
        Set<Group> groups = new HashSet<>(groupRepository.findAllById(categoryUpdateDTO.getGroupIds()));
        category.setGroups(groups);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}