package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.category.*;
import org.fh.documentmanagementservice.document.Document;
import org.fh.documentmanagementservice.document.DocumentRepository;
import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

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
    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createCategorySuccessfully() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Test", new HashSet<>(), new HashSet<>());
        Category category = new Category();
        category.setUsers(new HashSet<>());
        category.setDocuments(new HashSet<>());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.createCategory(requestDTO);

        assertNotNull(responseDTO);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void getAllCategoriesSuccessfully() {
        Category category = new Category();
        category.setUsers(new HashSet<>());
        category.setDocuments(new HashSet<>());
        Page<Category> categories = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categories);

        Page<CategoryResponseDTO> responseDTOs = categoryService.getAllCategories(PageRequest.of(0, 10));

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(categoryRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getCategoryByIdSuccessfully() {
        Category category = new Category();
        category.setUsers(new HashSet<>());
        category.setDocuments(new HashSet<>());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryResponseDTO responseDTO = categoryService.getCategoryById(1L);

        assertNotNull(responseDTO);
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void updateCategorySuccessfully() {
        Category category = new Category();
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Test", new HashSet<>(), new HashSet<>());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(1L, requestDTO);

        assertNotNull(responseDTO);
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void deleteCategorySuccessfully() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void convertUserIdsToUsersSuccessfully() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Set<User> users = categoryService.convertUserIdsToUsers(Set.of(1L));

        assertNotNull(users);
        assertFalse(users.isEmpty());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void convertDocumentIdsToDocumentsSuccessfully() {
        Document document = new Document();
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));

        Set<Document> documents = categoryService.convertDocumentIdsToDocuments(Set.of(1L));

        assertNotNull(documents);
        assertFalse(documents.isEmpty());
        verify(documentRepository, times(1)).findById(anyLong());
    }
}