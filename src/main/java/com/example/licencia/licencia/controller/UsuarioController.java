package com.example.licencia.licencia.controller;

import com.example.licencia.licencia.dto.UsuarioDTO;
import com.example.licencia.licencia.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    private ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        if (Objects.equals(usuarioService.create(usuarioDTO), "OK")) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario");
        }
    }

    @PostMapping("/actualizar")
    private ResponseEntity<String> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        if (Objects.equals(usuarioService.update(usuarioDTO), "OK")) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario");
        }
    }

    @GetMapping("/traer")
    private ResponseEntity<String> traerUsuario(@RequestParam String usuario, @RequestParam String password) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(usuario);
        usuarioDTO.setPassword(password);
        if(Objects.equals(usuarioService.findUsuario(usuarioDTO), "OK")) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario traer con exito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al traer usuario");
        }
    }

}
