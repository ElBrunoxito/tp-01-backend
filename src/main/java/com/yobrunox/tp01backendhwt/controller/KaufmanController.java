package com.yobrunox.tp01backendhwt.controller;

import com.yobrunox.tp01backendhwt.model.KaufmanLog;
import com.yobrunox.tp01backendhwt.service.KaufmanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("kaufman")
@Slf4j
public class KaufmanController {
    private final KaufmanService kaufmanService;

    @GetMapping("{id}")
    public ResponseEntity<KaufmanLog> getKaufmanLog(@PathVariable UUID id) {
        KaufmanLog kaufmanLog = kaufmanService.getKaufmanLogById(id);
        log.info("getKaufmanLog: {}", kaufmanLog.getId());
        return ResponseEntity.ok(kaufmanLog);
    }

    @GetMapping("{idChild}/getLast")
    public ResponseEntity<KaufmanLog> getLastKaufmanLog(@PathVariable UUID idChild) {
        KaufmanLog kaufmanLog = kaufmanService.getLastKaufmanLog(idChild);
        log.info("get last KaufmanLog: {}", kaufmanLog.getId());
        return ResponseEntity.ok(kaufmanLog);
    }

    @GetMapping("child/{idChild}")
    public ResponseEntity<List<KaufmanLog>> getKaufmanLogs(@PathVariable UUID idChild) {
        List<KaufmanLog> logs = kaufmanService.getAllKaufmanLogsByIdChild(idChild);
        log.info("getKaufmanLogs");
        return ResponseEntity.ok(logs);
    }

    @PostMapping("{idChild}")
    public ResponseEntity<KaufmanLog> create(@PathVariable UUID idChild,@RequestBody KaufmanLog request) {
        KaufmanLog kaufmanLog = kaufmanService.saveKaufmanLog(idChild,request);
        log.info("Create kaufmanLog: {}", kaufmanLog.getId());
        return ResponseEntity.ok(kaufmanLog);
    }


}
