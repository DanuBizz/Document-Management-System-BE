package org.fh.documentmanagementservice.group;

import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service class for Group related operations.
 * This class contains the business logic for the Group entity.
 */
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    /**
     * Create a new group.
     * @param groupRequestDTO
     * @return
     */
    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        Set<Long> userIds = userRepository.findAllByUsernameIn(groupRequestDTO.getUsernames())
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        group.setUserIds(userIds);
        Group savedGroup = groupRepository.save(group);
        return convertToGroupResponseDTO(savedGroup);
    }
    /**
     * Update an existing group.
     * @param id
     * @param groupRequestDTO
     * @return
     */
    public GroupResponseDTO updateGroup(Long id, GroupRequestDTO groupRequestDTO) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        group.setName(groupRequestDTO.getName());
        Set<Long> userIds = userRepository.findAllByUsernameIn(groupRequestDTO.getUsernames())
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        group.setUserIds(userIds);
        Group updatedGroup = groupRepository.save(group);
        return convertToGroupResponseDTO(updatedGroup);
    }
    /**
     * Get a group by its ID.
     * @param id
     * @return
     */
    public GroupResponseDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return convertToGroupResponseDTO(group);
    }
    /**
     * Get all groups.
     * @param pageable
     * @return
     */
    public Page<GroupResponseDTO> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).map(this::convertToGroupResponseDTO);
    }
    /**
     * Delete a group by its ID.
     * @param id
     */
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }
    /**
     * Convert a Group entity to a GroupResponseDTO.
     * @param group
     * @return
     */
    private GroupResponseDTO convertToGroupResponseDTO(Group group) {
        List<Long> userIds = new ArrayList<>(group.getUserIds());
        List<String> usernames = userRepository.findAllById(userIds)
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return new GroupResponseDTO(group.getId(), group.getName(), userIds, usernames);
    }
    /**
     * Search for groups by name.
     * @param search
     * @param pageable
     * @return
     */
    public Page<GroupResponseDTO> searchGroups(String search, Pageable pageable) {
        Page<Group> groupPage = groupRepository.findByNameStartingWithIgnoreCase(search, pageable);
        return groupPage.map(this::convertToGroupResponseDTO);
    }
    /**
     * Get all groups by user ID.
     * @param userId
     * @return
     */
    public List<Group> getGroupsByUserId(Long userId) {
        return groupRepository.findAllByUserIdsContains(userId);
    }
}
