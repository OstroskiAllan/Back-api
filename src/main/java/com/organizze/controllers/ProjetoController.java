package com.organizze.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.organizze.model.projeto.RegisterDTO;
import com.organizze.model.usuario.Usuario;
import com.organizze.repositories.ProjetoRepository;
import com.organizze.repositories.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/projeto")
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
    public ResponseEntity createProjeto(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid RegisterDTO data) {
        Long userId = ((Usuario) userDetails).getId();
  

        Projeto novoProjeto = new Projeto(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), userId);

        this.projetoRepository.save(novoProjeto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> getProjetoById(@AuthenticationPrincipal @PathVariable Integer id) {
        
        Optional<Projeto> projetos = projetoRepository.findById(id);
        return projetos.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((Usuario) userDetails).getId();

        List<Projeto> projetos = projetoRepository.findByUsuarioId(userId);

        if (projetos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ProjetoResponseDTO> projetoList = projetos.stream().map(ProjetoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projetoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProjeto(@AuthenticationPrincipal @PathVariable Integer id){
        if (!projetoRepository.existsById(id)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            projetoRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
