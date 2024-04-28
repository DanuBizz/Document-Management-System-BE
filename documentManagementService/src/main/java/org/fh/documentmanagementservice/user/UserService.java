package org.fh.documentmanagementservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setIsAdmin(userRequestDTO.getIsAdmin());
        user.setCategories(userRequestDTO.getCategories());

        User savedUser = userRepository.save(user);

        return convertToUserResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return convertToUserResponseDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequestDTO.getUsername());
            user.setEmail(userRequestDTO.getEmail());
            user.setIsAdmin(userRequestDTO.getIsAdmin());
            user.setCategories(userRequestDTO.getCategories());

            User updatedUser = userRepository.save(user);

            return convertToUserResponseDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setIsAdmin(user.getIsAdmin());
        userResponseDTO.setCategories(user.getCategories());

        return userResponseDTO;
    }
}