package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.KaufmanLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public interface KaufmanRepository extends JpaRepository<KaufmanLog, UUID> {
    Optional<KaufmanLog> findTopByChild_IdOrderByCreatedDateDesc(UUID idChild);

}
