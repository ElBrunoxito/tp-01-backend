package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.ChildRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChildRoutineRepository extends JpaRepository<ChildRoutine, UUID> {
    List<ChildRoutine> findAllByChild_Id(UUID id);
}
