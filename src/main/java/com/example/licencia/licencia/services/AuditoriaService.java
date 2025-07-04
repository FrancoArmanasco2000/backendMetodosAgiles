package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.AuditoriaDTO;
import com.example.licencia.licencia.dto.UsuarioDTO;
import com.example.licencia.licencia.models.Auditoria;
import com.example.licencia.licencia.models.Usuario;
import com.example.licencia.licencia.repository.AuditoriaRepository;
import com.example.licencia.licencia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService implements IAuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void create(AuditoriaDTO auditoriaDTO) {
        Auditoria auditoria =  new Auditoria();
        auditoria.setFechaEmision(auditoriaDTO.getFechaEmision());
        auditoria.setTramite(auditoriaDTO.getTramite());
        Usuario usuario = usuarioRepository.findUsuarioByUsuario(auditoriaDTO.getUsuario());
        auditoria.setUsuario(usuario);
        auditoriaRepository.save(auditoria);
    }

}
