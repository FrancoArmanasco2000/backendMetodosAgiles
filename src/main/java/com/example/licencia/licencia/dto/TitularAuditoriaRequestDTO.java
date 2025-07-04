package com.example.licencia.licencia.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TitularAuditoriaRequestDTO {

    private TitularDTO titular;
    private AuditoriaDTO auditoria;

}
