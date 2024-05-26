package org.fh.documentmanagementservice.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.createGroup(groupRequestDTO);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Group>> getAllGroups(Pageable pageable) {
        Page<Group> groupPage = groupService.getAllGroups(pageable);
        return ResponseEntity.ok(groupPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.updateGroup(id, groupRequestDTO);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Group>> searchGroup(@RequestParam String name, Pageable pageable) {
        Page<Group> groupPage = groupService.searchGroup(name, pageable);
        return ResponseEntity.ok(groupPage);
    }
}