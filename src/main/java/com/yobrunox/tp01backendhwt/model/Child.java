package com.yobrunox.tp01backendhwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn
    private User user;

    @ColumnTransformer(
            read = "pgp_sym_decrypt(name::bytea,'u7F9kPzQwX3h9L27oP0qR')",
            write = "pgp_sym_encrypt(?, 'u7F9kPzQwX3h9L27oP0qR')"
    )
    private String name;

    private Integer age;

    private String gender;

    private Integer levelTEA;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child",cascade = CascadeType.PERSIST)
    private List<KaufmanLog> kaufmanLogs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child",cascade = CascadeType.PERSIST)
    private List<ChildRoutine> routine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child",cascade = CascadeType.PERSIST)
    private List<TestTea> testTea;

}
