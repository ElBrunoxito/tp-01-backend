package com.yobrunox.tp01backendhwt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "child_routine")
public class ChildRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name="routine_id")
    private Routine routine;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

}
