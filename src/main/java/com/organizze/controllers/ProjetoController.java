package com.organizze.controllers;

import com.organizze.infra.TokenService;
import com.organizze.model.projeto.*;
import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.tarefa.TarefaRegisterDTO;
import com.organizze.model.usuario.*;
import com.organizze.model.usuario_projeto.*;
import com.organizze.repositories.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projeto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetoController {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private UsuarioProjetoRepository usuarioProjetoRepository;
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
   

    @PostMapping
    public ResponseEntity createProjeto(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid ProjetoRegisterDTO data) {
        Long userId = ((Usuario) userDetails).getId();

        Projeto novoProjeto = new Projeto(data.nome(), data.descricao(), data.data_inicio(), data.data_fim());
        this.projetoRepository.save(novoProjeto);

        UsuarioProjeto novoUsuarioProjeto = new UsuarioProjeto(userId, novoProjeto.getId(), "Cargo do usuário");
        this.usuarioProjetoRepository.save(novoUsuarioProjeto);

        return ResponseEntity.ok().build();
    }

    // @PostMapping("/{projetoId}/tarefa")
    // public ResponseEntity createTarefa(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long projetoId,
    //         @RequestBody @Valid TarefaRegisterDTO data) {
    //     Long userId = ((Usuario) userDetails).getId();

    //     Tarefa novaTarefa = new Tarefa(data.nome(), data.observacoes(), data.dataEntrega(), projetoId,
    //             data.statusId(), userId);
    //     this.tarefaRepository.save(novaTarefa);

    //     return ResponseEntity.ok().build();
    // }

    @PostMapping("/{projetoId}/usuario")
    public ResponseEntity addUsuarioAoProjeto(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projetoId, @RequestBody @Valid UsuarioProjetoRegisterDTO data) {
        Long userId = ((Usuario) userDetails).getId();

        UsuarioProjeto novoUsuarioProjeto = new UsuarioProjeto(data.usuarioId(), projetoId, data.cargo());
        this.usuarioProjetoRepository.save(novoUsuarioProjeto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> getProjetoById(@AuthenticationPrincipal @PathVariable Long id) {
        Optional<Projeto> projetos = projetoRepository.findById(id);
        return projetos.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((Usuario) userDetails).getId();
        List<UsuarioProjeto> usuarioProjetos = usuarioProjetoRepository.findByUsuarioId(userId);

        if (usuarioProjetos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ProjetoResponseDTO> projetoList = usuarioProjetos.stream()
                .map(up -> new ProjetoResponseDTO(projetoRepository.findById(up.getProjetoId()).get()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(projetoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProjeto(@AuthenticationPrincipal @PathVariable Long id) {
        if (!projetoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projetoRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}

/*
 * import java.util.Collections;
 * import java.util.List;
 * import java.util.Optional;
 * import java.util.stream.Collectors;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.security.authentication.AuthenticationManager;
 * import org.springframework.security.core.annotation.AuthenticationPrincipal;
 * import org.springframework.security.core.userdetails.UserDetails;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import com.organizze.infra.TokenService;
 * import com.organizze.model.projeto.Projeto;
 * import com.organizze.model.projeto.ProjetoResponseDTO;
 * import com.organizze.model.projeto.RegisterDTO;
 * import com.organizze.model.usuario.Usuario;
 * import com.organizze.repositories.ProjetoRepository;
 * import com.organizze.repositories.UsuarioRepository;
 * 
 * import jakarta.validation.Valid;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.RequestParam;
 * 
 * 
 * @RestController
 * 
 * @RequestMapping("/projeto")
 * 
 * @CrossOrigin(origins = "http://localhost:4200")
 * public class ProjetoController {
 * 
 * @Autowired
 * private AuthenticationManager authenticationManager;
 * 
 * @Autowired
 * private UsuarioRepository usuarioRepository;
 * 
 * @Autowired
 * private TokenService tokenService;
 * 
 * @Autowired
 * private ProjetoRepository projetoRepository;
 * 
 * @PostMapping
 * public ResponseEntity createProjeto(@AuthenticationPrincipal UserDetails
 * userDetails, @RequestBody @Valid RegisterDTO data) {
 * Long userId = ((Usuario) userDetails).getId();
 * 
 * 
 * Projeto novoProjeto = new Projeto(data.nome(), data.descricao(),
 * data.data_inicio(), data.data_fim());
 * 
 * this.projetoRepository.save(novoProjeto);
 * return ResponseEntity.ok().build();
 * }
 * 
 * @GetMapping("/{id}")
 * public ResponseEntity<Projeto>
 * getProjetoById(@AuthenticationPrincipal @PathVariable Integer id) {
 * 
 * Optional<Projeto> projetos = projetoRepository.findById(id);
 * return projetos.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
 * .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
 * }
 * 
 * @GetMapping
 * public ResponseEntity<List<ProjetoResponseDTO>>
 * getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
 * Long userId = ((Usuario) userDetails).getId();
 * 
 * List<Projeto> projetos = projetoRepository.findByUsuarioId(userId);
 * 
 * if (projetos.isEmpty()) {
 * return ResponseEntity.ok(Collections.emptyList());
 * }
 * 
 * List<ProjetoResponseDTO> projetoList =
 * projetos.stream().map(ProjetoResponseDTO::new)
 * .collect(Collectors.toList());
 * return ResponseEntity.ok(projetoList);
 * }
 * 
 * @DeleteMapping("/{id}")
 * public ResponseEntity deleteProjeto(@AuthenticationPrincipal @PathVariable
 * Integer id){
 * if (!projetoRepository.existsById(id)) {
 * return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 * }
 * projetoRepository.deleteById(id);
 * 
 * return ResponseEntity.ok().build();
 * }
 * }
 */