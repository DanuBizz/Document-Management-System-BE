package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.user.*;
import org.fh.documentmanagementservice.category.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@Import(UserService.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void createUserSuccessfully() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email@example.com", true, Set.of(1L));
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setIsAdmin(true);
        Category category = new Category();
        category.setId(1L);
        user.setCategories(Set.of(category));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO responseDTO = userService.createUser(userRequestDTO);

        assertEquals(1L, responseDTO.getId());
        assertEquals("username", responseDTO.getUsername());
        assertEquals("email@example.com", responseDTO.getEmail());
        assertTrue(responseDTO.getIsAdmin());
        assertEquals(Set.of(1L), responseDTO.getCategoryIds());
    }

    @Test
    public void getAllUsersSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setIsAdmin(true);
        Category category = new Category();
        category.setId(1L);
        user.setCategories(Set.of(category));

        Page<User> users = mock(Page.class);
        when(users.getContent()).thenReturn(List.of(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(users);

        Page<UserResponseDTO> responseDTOs = mock(Page.class);
        when(responseDTOs.getContent()).thenReturn(List.of(new UserResponseDTO(1L, "username", "email@example.com", true, Set.of(1L))));
        when(userService.getAllUsers(PageRequest.of(0, 10))).thenReturn(responseDTOs);

        assertEquals(1, responseDTOs.getContent().size());
        UserResponseDTO responseDTO = responseDTOs.getContent().get(0);
        assertEquals(1L, responseDTO.getId());
        assertEquals("username", responseDTO.getUsername());
        assertEquals("email@example.com", responseDTO.getEmail());
        assertTrue(responseDTO.getIsAdmin());
        assertEquals(Set.of(1L), responseDTO.getCategoryIds());
    }

    @Test
    public void getUserSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setIsAdmin(true);
        Category category = new Category();
        category.setId(1L);
        user.setCategories(Set.of(category));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO responseDTO = userService.getUser(1L);

        assertEquals(1L, responseDTO.getId());
        assertEquals("username", responseDTO.getUsername());
        assertEquals("email@example.com", responseDTO.getEmail());
        assertTrue(responseDTO.getIsAdmin());
        assertEquals(Set.of(1L), responseDTO.getCategoryIds());
    }

    @Test
    public void updateUserSuccessfully() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("newUsername", "newEmail@example.com", false, Set.of(1L));
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setIsAdmin(true);
        Category category = new Category();
        category.setId(1L);
        user.setCategories(new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO responseDTO = userService.updateUser(1L, userRequestDTO);

        assertEquals(1L, responseDTO.getId());
        assertEquals("newUsername", responseDTO.getUsername());
        assertEquals("newEmail@example.com", responseDTO.getEmail());
        assertFalse(responseDTO.getIsAdmin());
        assertEquals(Set.of(1L), responseDTO.getCategoryIds());
    }

    @Test
    public void deleteUserSuccessfully() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUser(1L));
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, new UserRequestDTO()));
    }

    @Test
    public void categoryNotFound() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email@example.com", true, Set.of(1L));

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.createUser(userRequestDTO));
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userRequestDTO));
    }
}