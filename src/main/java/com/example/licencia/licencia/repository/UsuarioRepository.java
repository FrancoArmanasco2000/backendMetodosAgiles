package com.example.licencia.licencia.repository;

import com.example.licencia.licencia.models.Licencia;
import com.example.licencia.licencia.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    public Usuario findUsuarioByUsuarioAndPassword(String usuario, String password);
    public Usuario findUsuarioByUsuario(String username);
    List<Usuario> usuario(String usuario);
}
