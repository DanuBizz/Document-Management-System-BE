package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.category.*;
import org.fh.documentmanagementservice.exception.ResourceNotFoundException;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCategories_returnsPageOfCategories() {
        Page<Category> categoryPage = mock(Page.class);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);

        assertEquals(categoryPage, categoryService.getAllCategories(mock(Pageable.class)));
    }

    @Test
    public void getCategoryById_existingId_returnsCategory() {
        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        assertEquals(category, categoryService.getCategoryById(1L).get());
    }

    @Test
    public void createCategory_validRequest_returnsCategory() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Test");
        categoryRequestDTO.setUserIds(new HashSet<>(Arrays.asList(1L, 2L)));

        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAllById(anyList())).thenReturn(Arrays.asList(user1, user2));

        Category category = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        assertEquals(category, categoryService.createCategory(categoryRequestDTO));
    }

    @Test
    public void updateCategory_existingId_returnsUpdatedCategory() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Test");
        categoryRequestDTO.setUserIds(new HashSet<>(Arrays.asList(1L, 2L)));

        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAllById(anyList())).thenReturn(Arrays.asList(user1, user2));

        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        assertEquals(category, categoryService.updateCategory(1L, categoryRequestDTO));
    }

    @Test
    public void updateCategory_nonExistingId_throwsException() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Test");
        categoryRequestDTO.setUserIds(new HashSet<>(Arrays.asList(1L, 2L)));

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(1L, categoryRequestDTO));
    }

    @Test
    public void deleteCategory_existingId_noExceptionThrown() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
    }

    @Test
    public void deleteCategory_nonExistingId_throwsException() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}