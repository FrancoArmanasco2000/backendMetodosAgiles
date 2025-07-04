package com.example.licencia.licencia.repository;

import com.example.licencia.licencia.dto.LicenciaDTO;
import com.example.licencia.licencia.models.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LicenciaRepository extends JpaRepository<Licencia, Long> {
    List<Licencia> findByFechaVencimientoBefore(LocalDate fechaVencimientoBefore);
    List<Licencia> findByTitular_NombreContainingIgnoreCaseOrTitular_ApellidoContainingIgnoreCaseOrTitular_GrupoSangreContainingIgnoreCaseOrTitular_Donante(
            Optional<String> nombre, Optional<String> apellido, Optional<String> grupoSangre, Optional<Boolean> donante);
    Licencia findByTitular_nroDocumentoAndClaseAndAnuladaFalse(String nroDocumento, String clase);

}
