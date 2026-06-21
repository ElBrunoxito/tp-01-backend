package com.yobrunox.tp01backendhwt.controller;


import com.yobrunox.tp01backendhwt.dto.RoutineGetDTO;
import com.yobrunox.tp01backendhwt.service.RoutineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("routine")
@Slf4j
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping(value = "{idChild}/{idRoutine}")
    public ResponseEntity<RoutineGetDTO> registerRoutine(@PathVariable UUID idChild, @PathVariable Long idRoutine){
        RoutineGetDTO get = routineService.save(idChild, idRoutine);
        log.info("routine register: {}, para el niño con id: {}", get.getId(),idChild);
        return ResponseEntity.ok(get);
    }

    @GetMapping("{idChild}")
    public ResponseEntity<List<RoutineGetDTO>> getRoutinesByIdChild(@PathVariable UUID idChild){
        List<RoutineGetDTO> get = routineService.getRoutinesByIdChild(idChild);
        log.info("get routines by id child: {}",idChild);
        return ResponseEntity.ok(get);
    }
}
