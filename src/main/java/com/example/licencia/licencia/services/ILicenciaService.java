package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.LicenciaDTO;
import com.example.licencia.licencia.dto.LicenciaTItularDTO;

import java.util.List;
import java.util.Optional;

public interface ILicenciaService {

    public String create(LicenciaDTO licenciaDTO);

    public String renovar(LicenciaDTO licenciaDTO);

    public String copia(String clase, String nroDocumento);

    public List<LicenciaTItularDTO> licenciasExpiradas();

    public List<LicenciaTItularDTO> findLicencias(Optional<String> nombre, Optional<String>  apellido, Optional<String> grupoSangre, Optional<Boolean> donante);
}
