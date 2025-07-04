package com.example.licencia.licencia.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;
    private String clase;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String observaciones;
    private String cuit;
    private Double costo;
    @ManyToOne
    @JoinColumn(name = "titularId")
    private Titular titular;
    private boolean anulada;

}