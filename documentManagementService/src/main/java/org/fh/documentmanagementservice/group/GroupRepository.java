package org.fh.documentmanagementservice.group;

import org.fh.documentmanagementservice.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Page<Group> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    List<Group> findAllByNameIn(List<String> groupNames);

    List<Group> findAllByUserIdsContains(Long userId);
}