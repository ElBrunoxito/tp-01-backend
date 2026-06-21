package com.yobrunox.tp01backendhwt.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 250, min = 3, message = "El nombre debe tener entre 5 y 250 caracteres")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    private String username;

    @NotBlank(message = "El trabajo es obligatorio")
    private String work;

    @Pattern(regexp = "^$|^.{5,50}$", message = "La nueva contraseña debe tener entre 5 y 50 caracteres")
    private String password;

    @Pattern(regexp = "^$|^.{5,50}$", message = "La contraseña debe tener entre 5 y 50 caracteres")
    private String currentPassword;


    //Child
    @NotBlank(message = "El nombre del niño es obligatorio")
    @Size(max = 250, min = 2, message = "El nombre del niño debe tener entre 5 y 250 caracteres")
    private String nameChild;

    @NotNull(message = "La edad es requerida")
    @Min(value = 0,message = "La edad tiene que ser mayor que cero")
    private Integer ageChild;

    @NotBlank(message = "El genero del niño es obligatorio")
    @Size(max = 9, min = 1, message = "El genero debe tener entre 1 y 9 caracteres")
    private String genderChild;

}