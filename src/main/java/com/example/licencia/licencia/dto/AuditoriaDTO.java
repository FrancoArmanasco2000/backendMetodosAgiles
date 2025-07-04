package com.example.licencia.licencia.dto;

import com.example.licencia.licencia.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaDTO {

    private String usuario;
    private LocalDate fechaEmision;
    private String tramite;

}
