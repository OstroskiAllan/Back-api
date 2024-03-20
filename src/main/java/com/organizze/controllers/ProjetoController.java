package com.organizze.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.organizze.infra.TokenService;
import com.organizze.model.projeto.Projeto;
import com.organizze.model.projeto.ProjetoResponseDTO;
import com.organizze.model.usuario.Usuario;
import com.organizze.repositories.ProjetoRepository;
import com.organizze.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projetos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProjetoRepository projetoRepository;

    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody Projeto projeto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        // Obtém o ID do usuário logado a partir do UserDetails
        Long userId = ((Usuario) userDetails).getId();
        //Usuario usuario = (Usuario) userDetails;
        // Associa o projeto ao usuário pelo ID
        projeto.setUsuarioId(userId);
                                                    
        // Lógica para criar um novo projeto
        Projeto projetoCriado = projetoRepository.save(projeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoCriado);
    }

    @GetMapping("/projeto")
    public ResponseEntity getAllberto(){
        List<ProjetoResponseDTO> projetoList = this.projetoRepository.findAll().stream().map(ProjetoResponseDTO::new).toList();

        return ResponseEntity.ok(projetoList);
    }

}
