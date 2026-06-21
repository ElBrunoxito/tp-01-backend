package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<Child, UUID> {
    Optional<Child> findByUser_Id(UUID userId);
}
