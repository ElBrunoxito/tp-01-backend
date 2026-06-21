package com.yobrunox.tp01backendhwt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestTeaDTO {
    @NotNull(message = "El id del niño no puede ser nulo")
    private Map<String,Integer> test;
    private UUID idChild;

    public Integer getTotalScore() {
        if (test == null || test.isEmpty()) {
            return 0;
        }
        return test.values().stream().mapToInt(Integer::intValue).sum();
    }
}
