package com.yobrunox.tp01backendhwt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "test-tea")
public class TestTea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer points;
    private Integer levelTEA;
    private String obs;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="child_id")
    @JsonIgnore
    private Child child;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

}
