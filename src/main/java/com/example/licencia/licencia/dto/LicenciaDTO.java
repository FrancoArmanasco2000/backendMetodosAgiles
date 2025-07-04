package com.example.licencia.licencia.dto;

import com.example.licencia.licencia.models.Titular;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaDTO {

    private String clase;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private String cuit;
    private Double costo;
    private String nroDocumentoTitular;

}
