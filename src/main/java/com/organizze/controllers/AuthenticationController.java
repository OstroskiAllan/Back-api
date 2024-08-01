package com.organizze.controllers;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizze.infra.TokenService;
import com.organizze.model.usuario.AuthenticationDTO;
import com.organizze.model.usuario.LoginResponseDTO;
import com.organizze.model.usuario.Usuario;
import com.organizze.model.usuario.UsuarioRegisterDTO;
import com.organizze.repositories.UsuarioRepository;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        Usuario user = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        //var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        var response = new LoginResponseDTO(token, user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioRegisterDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario novoUsuario = new Usuario(data.nome(), data.email(), encryptedPassword);

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }

    // @GetMapping("/usuario/{id}")
    // public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
    //     Optional<Usuario> usuario = usuarioRepository.findById(id);
    //     return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
    //             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    // P/ registrar deixei este endpoint onde ele esta liberado sem autorizacao mas
    // ele esta validando token
    @GetMapping("/all")
    public String allAccess(@RequestHeader("Authorization") @Valid String token) {
        String authToken = token.replace("Bearer ", "");
        String toke1n = tokenService.validateToken(authToken);
        return "Public Content. - teste " + token + "       foi validado ou nao: " + toke1n;
    }

}