package com.example.licencia.licencia.controller;

import com.example.licencia.licencia.dto.AuditoriaDTO;
import com.example.licencia.licencia.dto.TitularAuditoriaRequestDTO;
import com.example.licencia.licencia.dto.TitularDTO;
import com.example.licencia.licencia.dto.UsuarioDTO;
import com.example.licencia.licencia.services.AuditoriaService;
import com.example.licencia.licencia.services.TitularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/titular")
public class TitularController {

    @Autowired
    TitularService titularService;
    @Autowired
    AuditoriaService  auditoriaService;

    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody TitularAuditoriaRequestDTO titularAuditoriaRequestDTO) {
        TitularDTO titularDTO = titularAuditoriaRequestDTO.getTitular();
        AuditoriaDTO auditoriaDTO = titularAuditoriaRequestDTO.getAuditoria();

        if(Objects.equals(titularService.create(titularDTO), "OK")) {
            auditoriaService.create(auditoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Titular creado con exito");
        } else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear titular");
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody TitularAuditoriaRequestDTO titularAuditoriaRequestDTO) {

        TitularDTO titularDTO = titularAuditoriaRequestDTO.getTitular();
        AuditoriaDTO auditoriaDTO = titularAuditoriaRequestDTO.getAuditoria();

        if(Objects.equals(titularService.update(titularDTO), "OK")) {
            auditoriaService.create(auditoriaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Titular actualizado con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar titular");
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<TitularDTO> getTitular(@RequestParam String nroDocumento) {
        if(titularService.findByNroDocumento(nroDocumento) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(titularService.findByNroDocumento(nroDocumento));
        }
    }

}
