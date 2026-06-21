package com.yobrunox.tp01backendhwt.repository;

import com.yobrunox.tp01backendhwt.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {

    Optional<Code> findByUser_UsernameAndCodeAndExpirationDateAfter(String username, String code, LocalDateTime expirationDateAfter);

    //List<Code> findByUser_UsernameAndExpirationDateAfter(String userUsername, LocalDateTime expirationDateAfter);
}
