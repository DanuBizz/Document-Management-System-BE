package org.fh.documentmanagementservice.group;

import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.fh.documentmanagementservice.user.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

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

    public GroupResponseDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return convertToGroupResponseDTO(group);
    }

    public Page<GroupResponseDTO> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).map(this::convertToGroupResponseDTO);
    }

    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }

    private GroupResponseDTO convertToGroupResponseDTO(Group group) {
        List<Long> userIds = new ArrayList<>(group.getUserIds());
        List<String> usernames = userRepository.findAllById(userIds)
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return new GroupResponseDTO(group.getId(), group.getName(), userIds, usernames);
    }

    public Page<GroupResponseDTO> searchGroups(String search, Pageable pageable) {
        Page<Group> groupPage = groupRepository.findByNameStartingWithIgnoreCase(search, pageable);
        return groupPage.map(this::convertToGroupResponseDTO);
    }
}
