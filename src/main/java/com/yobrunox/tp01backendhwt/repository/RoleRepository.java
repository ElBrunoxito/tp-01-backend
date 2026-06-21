package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
