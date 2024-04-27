package org.fh.documentmanagementservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User related operations.
 * This interface extends JpaRepository and provides methods for CRUD operations on User entities.
 * Spring Data JPA automatically creates a concrete class at runtime with the implementation of these methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}