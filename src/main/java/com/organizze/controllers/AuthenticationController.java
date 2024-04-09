package com.organizze.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizze.infra.TokenService;
import com.organizze.model.usuario.AuthenticationDTO;
import com.organizze.model.usuario.LoginResponseDTO;
import com.organizze.model.usuario.RegisterDTO;
import com.organizze.model.usuario.Usuario;
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

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario novoUsuario = new Usuario(data.nome(), data.email(), encryptedPassword);

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }

    // P/ registrar deixei este endpoint onde ele esta liberado sem autorizacao mas
    // ele esta validando token
    @GetMapping("/all")
    public String allAccess(@RequestHeader("Authorization") @Valid String token) {
        String authToken = token.replace("Bearer ", "");
        String toke1n = tokenService.validateToken(authToken);
        return "Public Content. - teste " + token + "       foi validado ou nao: " + toke1n;
    }

    //teste
    @GetMapping("/alll")
    public ResponseEntity<String> allAccessToken(@Valid @RequestHeader("Authorization") String token) {
        if (tokenService.validarToken(token)) {
            return ResponseEntity.ok("Closed content acessado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token INVALIDO!");
        }
    }

    //teste
    @GetMapping("/alla")
    public String alla() {
        return "TESSSSSSSSSSSSSSSSSSSSSSSSTE ok";
    }

    

    // @GetMapping("/alll")
    // public String allAccess1(@RequestHeader("Authorization") String token) {

    // String authToken = token.substring(7);
    // boolean toke1n = tokenService.validarToken(authToken);
    // return "Public Content. - teste " + authToken + " foi validado ou nao: " +
    // toke1n ;

    // }

    // @GetMapping("/close")
    // public ResponseEntity<String> closeAcess(@RequestHeader("Authorization")
    // String token) {
    // if (token == null || !token.startsWith("Bearer ")) {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sem token.");
    // }

    // String authToken = token.substring(7);
    // // Validando o token
    // if (tokenService.validarToken(authToken)) {
    // return ResponseEntity.ok("Closed content acessado com sucesso.");
    // } else {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token
    // INVALIDO!");
    // }
    // }

}