package org.fh.documentmanagementservice.group;

import org.fh.documentmanagementservice.user.User;
import org.fh.documentmanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        Set<User> users = new HashSet<>(userRepository.findAllByUsernameIn(groupRequestDTO.getUsernames()));
        group.setUsers(users);
        return groupRepository.save(group);
    }

    public Page<Group> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Group updateGroup(Long id, GroupRequestDTO groupRequestDTO) {
        Group group = getGroupById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        group.setName(groupRequestDTO.getName());
        Set<User> users = new HashSet<>(userRepository.findAllByUsernameIn(groupRequestDTO.getUsernames()));
        group.setUsers(users);
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }
}