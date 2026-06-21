package com.yobrunox.tp01backendhwt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KaufmanLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Integer icg;
    @Column(nullable = false)
    private Integer attention;
    @Column(nullable = false)
    private Integer memory;
    @Column(nullable = false)
    private Integer association;
    @Column(nullable = false)
    private Integer logicalSequencing;
    @Column(nullable = false)
    private Integer classification;
    @Column(nullable = false)
    private Integer visual;


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
