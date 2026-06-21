package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.TestTea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestTeaRepository extends JpaRepository<TestTea, Long> {
    List<TestTea> findAllByChild_Id(UUID id);
}
