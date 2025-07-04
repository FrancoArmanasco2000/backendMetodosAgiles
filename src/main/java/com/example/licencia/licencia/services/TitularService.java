package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.TitularDTO;
import com.example.licencia.licencia.models.Auditoria;
import com.example.licencia.licencia.models.Titular;
import com.example.licencia.licencia.repository.TitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitularService implements ITitularService {

    @Autowired
    private TitularRepository titularRepository;

    @Override
    public String create(TitularDTO titularDTO) {
        try {
            Titular titular = new Titular();
            titular.setNombre(titularDTO.getNombre());
            titular.setApellido(titularDTO.getApellido());
            titular.setDireccion(titularDTO.getDireccion());
            titular.setTipoDocumento(titularDTO.getTipoDocumento());
            titular.setNroDocumento(titularDTO.getNroDocumento());
            titular.setFechaNacimiento(titularDTO.getFechaNacimiento());
            titular.setGrupoSangre(titularDTO.getGrupoSangre());
            titular.setDonante(titularDTO.getDonante());
            titularRepository.save(titular);

            return "OK";
        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }

    @Override
    public String update(TitularDTO titularDTO) {
        try {
            Titular titular = titularRepository.findByNroDocumento(titularDTO.getNroDocumento());
            titular.setNombre(titularDTO.getNombre());
            titular.setApellido(titularDTO.getApellido());
            titular.setDireccion(titularDTO.getDireccion());
            titular.setTipoDocumento(titularDTO.getTipoDocumento());
            titular.setNroDocumento(titularDTO.getNroDocumento());
            titular.setFechaNacimiento(titularDTO.getFechaNacimiento());
            titular.setGrupoSangre(titularDTO.getGrupoSangre());
            titular.setDonante(titularDTO.getDonante());
            titularRepository.save(titular);
            return "OK";
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            return "Error " + e.getMessage();
        }
    }

    @Override
    public TitularDTO findByNroDocumento(String nroDocumento) {
        try {

            Titular titular = titularRepository.findByNroDocumento(nroDocumento);
            TitularDTO titularDTO = new TitularDTO();
            titularDTO.setNombre(titular.getNombre());
            titularDTO.setApellido(titular.getApellido());
            titularDTO.setDireccion(titular.getDireccion());
            titularDTO.setTipoDocumento(titular.getTipoDocumento());
            titularDTO.setNroDocumento(titular.getNroDocumento());
            titularDTO.setFechaNacimiento(titular.getFechaNacimiento());
            titularDTO.setGrupoSangre(titular.getGrupoSangre());
            titularDTO.setDonante(titular.getDonante());
            return titularDTO;
        } catch (Exception e) {
            return null;
        }
    }
}
