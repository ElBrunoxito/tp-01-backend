package com.yobrunox.tp01backendhwt.dto;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
    private UUID id;
    private String token;
    private String name;
    private String username;
    private String work;
    private UUID idChild;
    private String nameChild;
    private Integer ageChild;
    private String genderChild;
    private Integer levelTEA;
    private Integer currentProgress = 0;
}
