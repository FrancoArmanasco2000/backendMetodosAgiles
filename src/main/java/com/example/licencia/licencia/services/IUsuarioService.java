package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.UsuarioDTO;

public interface IUsuarioService {

    public String findUsuario(UsuarioDTO usuarioDTO);
    public String create(UsuarioDTO usuarioDTO);
    public String update(UsuarioDTO usuarioDTO);

}
