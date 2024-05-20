package org.fh.documentmanagementservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for User related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on User entities.
 * Spring Data JPA automatically creates a concrete class at runtime with the implementation of these methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    @Query(value = "SELECT * FROM user u WHERE u.id = :id", nativeQuery = true)
    User findByUserId(long id);

    @Query(value = "SELECT * FROM user u WHERE u.username = :userLogonName", nativeQuery = true)
    User findByUserLogonName(String userLogonName);
}