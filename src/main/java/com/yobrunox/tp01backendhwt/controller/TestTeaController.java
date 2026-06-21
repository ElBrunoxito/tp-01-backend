package com.yobrunox.tp01backendhwt.controller;

import com.yobrunox.tp01backendhwt.dto.GetTestTeaDTO;
import com.yobrunox.tp01backendhwt.dto.TestTeaDTO;
import com.yobrunox.tp01backendhwt.model.TestTea;
import com.yobrunox.tp01backendhwt.service.TestTeaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("test-tea")
@Slf4j
public class TestTeaController {

    private TestTeaService testTeaService;

    @PostMapping("process")
    public ResponseEntity<GetTestTeaDTO> process(@RequestBody @Valid TestTeaDTO test) {
        var result = testTeaService.processInformationTestTEA(test);
        log.info("Processing test tea with result: {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("{idTestTea}")
    public ResponseEntity<GetTestTeaDTO> getTestTea(@PathVariable("idTestTea") Long idTestTea) {
        GetTestTeaDTO result = testTeaService.getById(idTestTea);
        log.info("Get test tea with result: {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("list/{idChild}")
    public ResponseEntity<List<GetTestTeaDTO>> getChildTestTea(@PathVariable("idChild") UUID idChild) {
        var result = testTeaService.getByIdChild(idChild);
        log.info("Get List test tea for idChild: {}", idChild);
        return ResponseEntity.ok(result);
    }

}
