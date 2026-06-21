package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    UUID id(UUID id);
}
