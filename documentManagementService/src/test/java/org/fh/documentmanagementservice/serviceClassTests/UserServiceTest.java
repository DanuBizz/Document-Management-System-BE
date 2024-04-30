package org.fh.documentmanagementservice.serviceClassTests;

import org.fh.documentmanagementservice.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_validRequest_returnsUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("Test");
        userRequestDTO.setEmail("test@test.com");
        userRequestDTO.setIsAdmin(true);

        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertNotNull(userService.createUser(userRequestDTO));
    }

    @Test
    public void getUserById_existingId_returnsUser() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertNotNull(userService.getUserById(1L));
    }

    @Test
    public void getUserById_nonExistingId_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void updateUser_existingId_returnsUpdatedUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("Test");
        userRequestDTO.setEmail("test@test.com");
        userRequestDTO.setIsAdmin(true);

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertNotNull(userService.updateUser(1L, userRequestDTO));
    }

    @Test
    public void updateUser_nonExistingId_throwsException() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("Test");
        userRequestDTO.setEmail("test@test.com");
        userRequestDTO.setIsAdmin(true);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userRequestDTO));
    }

    @Test
    public void deleteUser_existingId_noExceptionThrown() {
        when(userRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    public void deleteUser_nonExistingId_throwsException() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }
}
