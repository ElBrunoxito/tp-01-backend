package com.yobrunox.tp01backendhwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineGetDTO {
    private UUID id;
    private LocalDateTime createdDate;
    private String activityName;
    private Integer level;
    private String status;
}
