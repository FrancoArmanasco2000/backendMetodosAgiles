package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.UsuarioDTO;
import com.example.licencia.licencia.models.Usuario;
import com.example.licencia.licencia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //Buscar usuario
    @Override
    public String findUsuario(UsuarioDTO usuarioDTO) {
        try {
            if(usuarioRepository.findUsuarioByUsuarioAndPassword(usuarioDTO.getUsuario(), usuarioDTO.getPassword()) != null) {
                return "OK";
            } else {
                return "NO ENCONTRADO";
            }
        } catch (Exception e) {
            return null;
        }
    }


    //creando usuario
    @Override
    public String create(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setUsuario(usuarioDTO.getUsuario());
            usuario.setPassword(usuarioDTO.getPassword());
            usuarioRepository.save(usuario);
            return "OK";
        } catch (Exception e) {
            return "Error "+ e.getMessage();
        }
    }

    @Override
    public String update(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioRepository.findUsuarioByUsuario(usuarioDTO.getUsuario());
            usuario.setUsuario(usuarioDTO.getUsuario());
            usuario.setPassword(usuarioDTO.getPassword());
            usuarioRepository.save(usuario);
            return "OK";
        } catch (Exception e) {
            return "Error "+ e.getMessage();
        }
    }
}
