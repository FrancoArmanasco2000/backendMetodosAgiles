package com.example.licencia.licencia.repository;

import com.example.licencia.licencia.models.Licencia;
import com.example.licencia.licencia.models.Titular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitularRepository extends JpaRepository<Titular, Long> {
    Titular findByNroDocumento(String nroDocumento);
}
