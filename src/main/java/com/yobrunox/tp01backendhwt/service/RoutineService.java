package com.yobrunox.tp01backendhwt.service;

import com.yobrunox.tp01backendhwt.dto.RoutineGetDTO;
import com.yobrunox.tp01backendhwt.exception.BusinessException;
import com.yobrunox.tp01backendhwt.model.Child;
import com.yobrunox.tp01backendhwt.model.ChildRoutine;
import com.yobrunox.tp01backendhwt.model.Routine;
import com.yobrunox.tp01backendhwt.repository.ChildRepository;
import com.yobrunox.tp01backendhwt.repository.ChildRoutineRepository;
import com.yobrunox.tp01backendhwt.repository.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoutineService {

    private final ChildRoutineRepository childRoutineRepository;
    private final RoutineRepository routineRepository;
    private final ChildRepository childRepository;

    //Completado

    public RoutineGetDTO save(UUID idChild, Long idRoutine){
        Child child = childRepository.findById(idChild).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Child not found"));

        Routine routine = routineRepository.findById(idRoutine).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Routine not found"));

        ChildRoutine childRoutine = ChildRoutine.builder()
                .status("Completado")
                .child(child)
                .routine(routine)
                .build();
        childRoutine = childRoutineRepository.save(childRoutine);
        return RoutineGetDTO.builder()
                .id(childRoutine.getId())
                .createdDate(childRoutine.getCreatedDate())
                .activityName(routine.getTitle())
                .level(routine.getLevel())
                .status(childRoutine.getStatus())
                .build();
    }


    public List<RoutineGetDTO> getRoutinesByIdChild(UUID idChild){
        return childRoutineRepository.findAllByChild_Id(idChild).stream()
                .map(rc->RoutineGetDTO.builder()
                            .id(rc.getId())
                            .createdDate(rc.getCreatedDate())
                            .activityName(rc.getRoutine().getTitle())
                            .level(rc.getRoutine().getLevel())
                            .status(rc.getStatus())
                            .build()).collect(Collectors.toList());

    }
}
