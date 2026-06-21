package com.yobrunox.tp01backendhwt.service;

import com.yobrunox.tp01backendhwt.exception.BusinessException;
import com.yobrunox.tp01backendhwt.model.Child;
import com.yobrunox.tp01backendhwt.model.KaufmanLog;
import com.yobrunox.tp01backendhwt.repository.ChildRepository;
import com.yobrunox.tp01backendhwt.repository.KaufmanRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KaufmanService {

    private final KaufmanRepository kaufmanRepository;
    private final ChildRepository childRepository;


    public KaufmanLog getKaufmanLogById(UUID id) {
        return kaufmanRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "KaufmanLog not found")
        );
    }

    public KaufmanLog getLastKaufmanLog(UUID idChild) {
        return kaufmanRepository.findTopByChild_IdOrderByCreatedDateDesc(idChild)
                .orElse(KaufmanLog.builder()
                        .id(UUID.randomUUID())
                        .icg(0)
                        .attention(0)
                        .memory(0)
                        .association(0)
                        .logicalSequencing(0)
                        .classification(0)
                        .visual(0)
                        .createdDate(LocalDateTime.now())
                        .build());
    }

    public List<KaufmanLog> getAllKaufmanLogsByIdChild(UUID idChild) {
        List<KaufmanLog> kaufmanLogs = childRepository.findById(idChild)
                .orElseThrow(
                        () -> new BusinessException(HttpStatus.NOT_FOUND, "Child with id: " + idChild + " not found")
                ).getKaufmanLogs();
        if (kaufmanLogs.isEmpty()) {
            return new ArrayList<>();
        }
        return kaufmanLogs;
    }

    public KaufmanLog saveKaufmanLog(UUID idChild, KaufmanLog kaufmanLog) {
        Child child = childRepository.findById(idChild)
                .orElseThrow(()-> new BusinessException(HttpStatus.NOT_FOUND, "Child with id: " + idChild + " not found"));
        kaufmanLog.setChild(child);
        return kaufmanRepository.save(kaufmanLog);
    }

}
