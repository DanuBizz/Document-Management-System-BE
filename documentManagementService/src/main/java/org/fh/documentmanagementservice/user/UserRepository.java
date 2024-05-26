package org.fh.documentmanagementservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for User related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on User entities.
 * Spring Data JPA automatically creates a concrete class at runtime with the implementation of these methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE u.id = :id", nativeQuery = true)
    User findByUserId(long id);

    @Query(value = "SELECT * FROM users u WHERE u.username = :userLogonName", nativeQuery = true)
    User findByUserUsername(String userLogonName);
}