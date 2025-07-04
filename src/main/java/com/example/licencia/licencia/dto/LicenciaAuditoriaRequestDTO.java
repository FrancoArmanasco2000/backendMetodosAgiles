package com.example.licencia.licencia.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaAuditoriaRequestDTO {

    LicenciaDTO licencia;
    AuditoriaDTO auditoria;

}
