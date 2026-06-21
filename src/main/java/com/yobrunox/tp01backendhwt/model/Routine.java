package com.yobrunox.tp01backendhwt.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "routine")
public class Routine {
    @Id
    private Long id;

    private String title;

    private Integer level;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "routine",cascade = CascadeType.PERSIST)
    private List<ChildRoutine> routine;

}
