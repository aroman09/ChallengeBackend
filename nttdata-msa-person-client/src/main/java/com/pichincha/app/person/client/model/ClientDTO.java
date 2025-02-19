package com.pichincha.app.person.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO{
    private Long clientId;

    @NotBlank(message = "El parametro nombre no debe estar en blanco")
    private String name;

    @NotBlank(message = "El parametro genero no debe estar en blanco")
    private String gender;

    @NotNull(message = "El parametro edad no debe estar en blanco")
    @Positive(message="El parametro edad debe ser un valor positivo")
    @Max(value = 90, message="El parametro edad debe estar en en un rango de entre 1 y 90 años")
    private Integer age;

    @NotBlank(message = "El parametro identificación no debe estar en blanco")
    @Size(min = 10,max = 13, message = "El parametro identificacion debe tener entre 10 y 13 caracteres")
    private String identification;

    @NotBlank(message = "El parametro dirección no debe estar en blanco")
    private String direction;

    @NotBlank(message = "El parametro teléfono no debe estar en blanco")
    private String telephone;

    @NotBlank(message = "El parametro password no debe estar en blanco")
    @Size (min = 8,max = 15, message = "El parametro password debe tener entre 8 y 15 caracteres")
    private String password;

    private boolean state;
}
