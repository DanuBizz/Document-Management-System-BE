package org.fh.documentmanagementservice.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for Group related operations.
 * This class handles the HTTP requests for the Group entity.
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    /**
     * Create a new group.
     * @param groupRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        GroupResponseDTO groupResponseDTO = groupService.createGroup(groupRequestDTO);
        return new ResponseEntity<>(groupResponseDTO, HttpStatus.CREATED);
    }
    /**
     * Update an existing group.
     * @param id
     * @param groupRequestDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(@PathVariable Long id, @RequestBody GroupRequestDTO groupRequestDTO) {
        GroupResponseDTO groupResponseDTO = groupService.updateGroup(id, groupRequestDTO);
        return new ResponseEntity<>(groupResponseDTO, HttpStatus.OK);
    }
    /**
     * Get a group by its ID.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable Long id) {
        GroupResponseDTO groupResponseDTO = groupService.getGroupById(id);
        return ResponseEntity.ok(groupResponseDTO);
    }
    /**
     * Get all groups.
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(Pageable pageable) {
        Page<GroupResponseDTO> groupPage = groupService.getAllGroups(pageable);
        return ResponseEntity.ok(groupPage);
    }
    /**
     * Delete a group by its ID.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search for groups by name.
     * @param search
     * @param pageable
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Page<GroupResponseDTO>> searchGroups(@RequestParam String search, Pageable pageable) {
        Page<GroupResponseDTO> groupPage = groupService.searchGroups(search, pageable);
        return ResponseEntity.ok(groupPage);
    }
}