package com.organizze.controllers;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizze.infra.TokenService;
import com.organizze.model.usuario.AuthenticationDTO;
import com.organizze.model.usuario.LoginResponseDTO;
import com.organizze.model.usuario.PasswordVerificationDTO;
import com.organizze.model.usuario.Usuario;
import com.organizze.model.usuario.UsuarioRegisterDTO;
import com.organizze.repositories.UsuarioRepository;

@RestController
@RequestMapping
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost" })
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

    @GetMapping("/all")
    public String allAccess(@RequestHeader("Authorization") @Valid String token) {
        String authToken = token.replace("Bearer ", "");
        String toke1n = tokenService.validateToken(authToken);
        return "Public Content. - teste " + token + "       foi validado ou nao: " + toke1n;
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("usuario/update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        Optional<Usuario> userOptional = usuarioRepository.findById(id);

        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();
            user.setNome(usuario.getNome());
            user.setEmail(usuario.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            usuarioRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Map<String, String>> verifyPassword(@RequestBody @Valid PasswordVerificationDTO data) {
        Optional<Long> usuarioIdOptional = usuarioRepository.findUserIdByEmail(data.getEmail());

        if (usuarioIdOptional.isPresent()) {
            Long usuarioId = usuarioIdOptional.get();
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                // Verifica se a senha atual está correta
                if (passwordEncoder.matches(data.getSenhaAtual(), usuario.getPassword())) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Senha atual está correta");
                    return ResponseEntity.ok(response);
                } else {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Senha atual está incorreta");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}

// P/ registrar deixei este endpoint onde ele esta liberado sem autorizacao mas
// ele esta validando token
