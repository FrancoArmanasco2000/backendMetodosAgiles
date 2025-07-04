package com.example.licencia.licencia.controller;

import com.example.licencia.licencia.dto.AuditoriaDTO;
import com.example.licencia.licencia.dto.LicenciaAuditoriaRequestDTO;
import com.example.licencia.licencia.dto.LicenciaDTO;
import com.example.licencia.licencia.dto.LicenciaTItularDTO;
import com.example.licencia.licencia.services.AuditoriaService;
import com.example.licencia.licencia.services.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/licencia")
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;
    @Autowired
    private AuditoriaService auditoriaService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearLicencia(@RequestBody LicenciaAuditoriaRequestDTO licenciaAuditoriaRequestDTO) {
        LicenciaDTO licenciaDTO = licenciaAuditoriaRequestDTO.getLicencia();
        AuditoriaDTO auditoriaDTO = licenciaAuditoriaRequestDTO.getAuditoria();

        if(Objects.equals(licenciaService.create(licenciaDTO), "OK")) {
            auditoriaService.create(auditoriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Licencia creada con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear Licencia");
        }
    }

    @PostMapping("/renovar")
    public ResponseEntity<String>  renovarLicencia(@RequestBody LicenciaAuditoriaRequestDTO licenciaAuditoriaRequestDTO) {
        LicenciaDTO licenciaDTO = licenciaAuditoriaRequestDTO.getLicencia();
        AuditoriaDTO  auditoriaDTO = licenciaAuditoriaRequestDTO.getAuditoria();

        if(Objects.equals(licenciaService.renovar(licenciaDTO), "OK")) {
            auditoriaService.create(auditoriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Licencia renovada con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al renovar");
        }
    }

    @PostMapping("/copia")
    public ResponseEntity<String> copiarLicencia(@RequestBody LicenciaAuditoriaRequestDTO licenciaAuditoriaRequestDTO) {
        LicenciaDTO licenciaDTO = licenciaAuditoriaRequestDTO.getLicencia();
        AuditoriaDTO  auditoriaDTO = licenciaAuditoriaRequestDTO.getAuditoria();

        if(Objects.equals(licenciaService.copia(licenciaDTO.getClase(),licenciaDTO.getNroDocumentoTitular()), "OK")) {
            auditoriaService.create(auditoriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Licencia copiada con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al copiar");
        }
    }

    @GetMapping ("/listarVencidos")
    public ResponseEntity<List<LicenciaTItularDTO>> listarVencidos() {
        return ResponseEntity.status(HttpStatus.OK).body(licenciaService.licenciasExpiradas());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LicenciaTItularDTO>> listar(@RequestParam Optional<String> nombre, @RequestParam Optional<String> apellido, @RequestParam Optional<String> grupoSangre, @RequestParam Optional<Boolean> donante) {
        return ResponseEntity.status(HttpStatus.OK).body(licenciaService.findLicencias(nombre, apellido, grupoSangre, donante));
    }

}
