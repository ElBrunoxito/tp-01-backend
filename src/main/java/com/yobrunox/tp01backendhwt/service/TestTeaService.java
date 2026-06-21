package com.yobrunox.tp01backendhwt.service;

import com.yobrunox.tp01backendhwt.dto.GetTestTeaDTO;
import com.yobrunox.tp01backendhwt.dto.TestTeaDTO;
import com.yobrunox.tp01backendhwt.exception.BusinessException;
import com.yobrunox.tp01backendhwt.model.Child;
import com.yobrunox.tp01backendhwt.model.TestTea;
import com.yobrunox.tp01backendhwt.repository.ChildRepository;
import com.yobrunox.tp01backendhwt.repository.TestTeaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(timeout = 20)
public class TestTeaService {

    private final TestTeaRepository testTeaRepository;
    private final ChildRepository childRepository;

    public GetTestTeaDTO getById(Long id) {
        return testTeaRepository.findById(id)
                .map(ttea -> GetTestTeaDTO.builder()
                        .id(ttea.getId())
                        .points(ttea.getPoints())
                        .levelTEA(ttea.getLevelTEA())
                        .obs(ttea.getObs())
                        .createdDate(ttea.getCreatedDate())
                        .build())
                .orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "TestTea with id " + id + " not found")
        );
    }
    public List<GetTestTeaDTO> getByIdChild(UUID idChild) {
        return testTeaRepository.findAllByChild_Id(idChild).stream()
                .map(ttea ->
                    GetTestTeaDTO.builder()
                            .id(ttea.getId())
                            .points(ttea.getPoints())
                            .levelTEA(ttea.getLevelTEA())
                            .obs(ttea.getObs())
                            .createdDate(ttea.getCreatedDate())
                            .build()
                )
                .collect(Collectors.toList());
    }
    //Return test tea
    public GetTestTeaDTO processInformationTestTEA(TestTeaDTO testTeaDTO) {
        Child child = childRepository.findById(testTeaDTO.getIdChild()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Child not found"));
        var ageChild = child.getAge();
        var questions = testTeaDTO.getTest();

        if(questions.size() != 20) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Ha ocurrido un error interno");
        }

        Integer points = testTeaDTO.getTotalScore();
        String obs = "";
        Integer level = 0;

        log.info("points: {}", points);
        if(0 <= points && points <= 2) {
            if(ageChild < 2){
                obs = "Repetir pruebas en su segundo cumpleaños";
            }
            level = 1;
        }else if(3 <= points && points <= 7) {
            level = 2;
        }else{
            level = 3;
        }
        child.setLevelTEA(level);
        child = childRepository.save(child);
        TestTea testTea = TestTea.builder()
                .points(points)
                .levelTEA(level)
                .obs(obs)
                .child(child)
                .build();
        testTea = testTeaRepository.save(testTea);
        return GetTestTeaDTO.builder()
                .id(testTea.getId())
                .points(testTea.getPoints())
                .levelTEA(testTea.getLevelTEA())
                .obs(testTea.getObs())
                .createdDate(testTea.getCreatedDate())
                .build();
    }
}
