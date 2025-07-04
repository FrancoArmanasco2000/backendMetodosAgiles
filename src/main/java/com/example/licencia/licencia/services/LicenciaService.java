package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.LicenciaDTO;
import com.example.licencia.licencia.dto.LicenciaTItularDTO;
import com.example.licencia.licencia.dto.TitularDTO;
import com.example.licencia.licencia.models.Licencia;
import com.example.licencia.licencia.models.Titular;
import com.example.licencia.licencia.repository.LicenciaRepository;
import com.example.licencia.licencia.repository.TitularRepository;
import com.example.licencia.licencia.utils.UseCaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LicenciaService implements ILicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    UseCaseUtils useCaseUtils = new UseCaseUtils();

    @Autowired
    private TitularRepository titularRepository;

    @Override
    public String create(LicenciaDTO licenciaDTO) {
        try {
            Licencia licencia = new Licencia();
            licencia.setClase(licenciaDTO.getClase());
            licencia.setCuit(licenciaDTO.getCuit());
            licencia.setObservaciones(licenciaDTO.getObservaciones());
            licencia.setFechaEmision(licenciaDTO.getFechaEmision());

            Titular titular = titularRepository.findByNroDocumento(licenciaDTO.getNroDocumentoTitular());
            licencia.setTitular(titular);
            licencia.setFechaVencimiento(useCaseUtils.calcularVigencia(titular));
            Period period = Period.between(licencia.getFechaEmision(),licencia.getFechaVencimiento());
            Integer vigenciaAnios =  period.getYears();
            licencia.setCosto(useCaseUtils.calcularCostoLicencia(licenciaDTO.getClase(), vigenciaAnios));


            licenciaRepository.save(licencia);

            return "OK";
        } catch(Exception e) {
            System.out.println("Error "+e.getMessage());
            return "Error "+e.getMessage();
        }
    }

    @Override
    public String renovar(LicenciaDTO licenciaDTO) {
        try {
            //Anulo la licencia vieja
            Licencia licenciaVieja = licenciaRepository.findByTitular_nroDocumentoAndClaseAndAnuladaFalse(licenciaDTO.getNroDocumentoTitular(), licenciaDTO.getClase());
            licenciaVieja.setAnulada(true);
            licenciaRepository.save(licenciaVieja);
        } catch (Exception ex) {
            System.out.println("Error TRY 1 "+ex.getMessage());
            return "Error " + ex.getMessage();
        }

        try{
            //Registro la nueva licencia
            return create(licenciaDTO);
        } catch (Exception e) {
            System.out.println("Error TRY 2 "+e.getMessage());
            return "Error " + e.getMessage();
        }

    }

    @Override
    public String copia(String clase, String nroDocumento) {
        try {
            Licencia licenciaVieja = licenciaRepository.findByTitular_nroDocumentoAndClaseAndAnuladaFalse(nroDocumento,clase);
            System.out.println(licenciaVieja);
            Licencia licenciaCopia = new Licencia();

            licenciaCopia.setAnulada(false);
            licenciaCopia.setFechaEmision(LocalDate.now());
            licenciaCopia.setFechaVencimiento(LocalDate.now());
            licenciaCopia.setCuit(licenciaVieja.getCuit());
            licenciaCopia.setClase(licenciaVieja.getClase());
            licenciaCopia.setObservaciones(licenciaVieja.getObservaciones());
            licenciaCopia.setTitular(licenciaVieja.getTitular());
            licenciaCopia.setCosto(50.0);
            licenciaRepository.save(licenciaCopia);

            licenciaVieja.setAnulada(true);
            licenciaRepository.save(licenciaVieja);

            return "OK";
        } catch (Exception e) {
            System.out.println("Error TRY 3 "+e.getMessage());
            return "Error " + e.getMessage();
        }
    }

    @Override
    public List<LicenciaTItularDTO> licenciasExpiradas() {
        List<Licencia> licencias = licenciaRepository.findByFechaVencimientoBefore(LocalDate.now());
        List<LicenciaTItularDTO> licenciasVencidas = new ArrayList<>();
        for(Licencia licencia : licencias) {
            LicenciaDTO licenciaDTO = new LicenciaDTO();
            licenciaDTO.setClase(licencia.getClase());
            licenciaDTO.setCuit(licencia.getCuit());
            licenciaDTO.setObservaciones(licencia.getObservaciones());
            licenciaDTO.setCosto(licencia.getCosto());
            licenciaDTO.setFechaVencimiento(licencia.getFechaVencimiento());
            licenciaDTO.setFechaEmision(licencia.getFechaEmision());

            TitularDTO titularDTO = new TitularDTO();
            titularDTO.setNombre(licencia.getTitular().getNombre());
            titularDTO.setApellido(licencia.getTitular().getApellido());
            titularDTO.setDireccion(licencia.getTitular().getDireccion());
            titularDTO.setTipoDocumento(licencia.getTitular().getTipoDocumento());
            titularDTO.setNroDocumento(licencia.getTitular().getNroDocumento());
            titularDTO.setFechaNacimiento(licencia.getTitular().getFechaNacimiento());
            titularDTO.setGrupoSangre(licencia.getTitular().getGrupoSangre());
            titularDTO.setDonante(licencia.getTitular().getDonante());
            //titularDTO.setLicencias(licencia.getTitular().getLicencias());

            LicenciaTItularDTO licenciaTItularDTO = new LicenciaTItularDTO();
            licenciaTItularDTO.setLicenciaDTO(licenciaDTO);
            licenciaTItularDTO.setTitularDTO(titularDTO);

            licenciasVencidas.add(licenciaTItularDTO);
        }

        return licenciasVencidas;
    }

    @Override
    public List<LicenciaTItularDTO> findLicencias(Optional<String> nombre, Optional<String> apellido, Optional<String> grupoSangre, Optional<Boolean> donante) {
        try {
            List<Licencia> licencias = licenciaRepository.findByTitular_NombreContainingIgnoreCaseOrTitular_ApellidoContainingIgnoreCaseOrTitular_GrupoSangreContainingIgnoreCaseOrTitular_Donante(nombre, apellido, grupoSangre, donante);
            List<LicenciaTItularDTO> licenciasEncontradas = new ArrayList<>();
            for(Licencia licencia : licencias) {

                if(licencia.getFechaVencimiento().isBefore(LocalDate.now())) continue;

                LicenciaDTO licenciaDTO = new LicenciaDTO();
                licenciaDTO.setClase(licencia.getClase());
                licenciaDTO.setCuit(licencia.getCuit());
                licenciaDTO.setObservaciones(licencia.getObservaciones());
                licenciaDTO.setCosto(licencia.getCosto());
                licenciaDTO.setFechaVencimiento(licencia.getFechaVencimiento());
                licenciaDTO.setFechaEmision(licencia.getFechaEmision());

                TitularDTO titularDTO = new TitularDTO();
                titularDTO.setNombre(licencia.getTitular().getNombre());
                titularDTO.setApellido(licencia.getTitular().getApellido());
                titularDTO.setDireccion(licencia.getTitular().getDireccion());
                titularDTO.setTipoDocumento(licencia.getTitular().getTipoDocumento());
                titularDTO.setNroDocumento(licencia.getTitular().getNroDocumento());
                titularDTO.setFechaNacimiento(licencia.getTitular().getFechaNacimiento());
                titularDTO.setGrupoSangre(licencia.getTitular().getGrupoSangre());
                titularDTO.setDonante(licencia.getTitular().getDonante());
                //titularDTO.setLicencias(licencia.getTitular().getLicencias());

                LicenciaTItularDTO licenciaTItularDTO = new LicenciaTItularDTO();
                licenciaTItularDTO.setLicenciaDTO(licenciaDTO);
                licenciaTItularDTO.setTitularDTO(titularDTO);

                licenciasEncontradas.add(licenciaTItularDTO);
            }

            return licenciasEncontradas;
        } catch (Exception e) {
            System.out.println("Error "+e.getMessage());
            return null;
        }
    }


}
