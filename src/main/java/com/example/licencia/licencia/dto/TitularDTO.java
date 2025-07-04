package com.example.licencia.licencia.dto;

import com.example.licencia.licencia.models.Licencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {

    private String tipoDocumento;
    private String nroDocumento;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String grupoSangre;
    private Boolean donante;
    private List<Licencia> licencias;

}
