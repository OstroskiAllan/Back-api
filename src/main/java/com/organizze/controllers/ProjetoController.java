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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizze.infra.TokenService;
import com.organizze.model.projeto.Projeto;
import com.organizze.model.projeto.ProjetoRegisterDTO;
import com.organizze.model.projeto.ProjetoRequestDTO;
import com.organizze.model.projeto.ProjetoResponseDTO;
import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.usuario.Usuario;
import com.organizze.model.usuario_projeto.UsuarioProjeto;
import com.organizze.model.usuario_projeto.UsuarioProjetoDTO;
import com.organizze.model.usuario_projeto.UsuarioProjetoId;
import com.organizze.model.usuario_projeto.UsuarioProjetoRegisterDTO;
import com.organizze.repositories.ProjetoRepository;
import com.organizze.repositories.TarefaRepository;
import com.organizze.repositories.UsuarioProjetoRepository;
import com.organizze.repositories.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/projeto")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost" })
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

        Projeto novoProjeto = new Projeto(data.nome(), data.descricao(), data.dataInicio(), data.dataFim());
        this.projetoRepository.save(novoProjeto);

        UsuarioProjeto novoUsuarioProjeto = new UsuarioProjeto(userId, novoProjeto.getId(), "Gerente");
        this.usuarioProjetoRepository.save(novoUsuarioProjeto);

        return ResponseEntity.ok().build();
    }

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

    // ISOLADO ESSE DIACHO
    // ----------------------------------------------------------------

    @GetMapping("/part/{id}")
    public ResponseEntity<List<UsuarioProjeto>> getProjetoPartById(@AuthenticationPrincipal @PathVariable Long id) {
        List<UsuarioProjeto> usuarioProjetos = usuarioProjetoRepository.findByUsuarioIdAndNotGerente(id);

        if (usuarioProjetos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuarioProjetos, HttpStatus.OK);
    }
    // ISOLADO ----------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((Usuario) userDetails).getId();
        List<UsuarioProjeto> usuarioProjetos = usuarioProjetoRepository.findByUsuarioIdAndGerente(userId);

        if (usuarioProjetos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ProjetoResponseDTO> projetoList = usuarioProjetos.stream()
                .map(up -> new ProjetoResponseDTO(projetoRepository.findById(up.getProjetoId()).get()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(projetoList);
    }

    @GetMapping("/{id}/nome")
    public ResponseEntity<String> getUserName(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            String nomeUsuario = usuario.get().getNome(); // Acessa o nome do usuário
            return ResponseEntity.ok(nomeUsuario);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProjeto(@AuthenticationPrincipal @PathVariable Long id) {
        if (!projetoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projetoRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    // busca o time
    @GetMapping("/team/{id}")
    public ResponseEntity<List<UsuarioProjeto>> getTeamByProjectId(@AuthenticationPrincipal @PathVariable Long id) {
        List<UsuarioProjeto> usuarioProjetos = usuarioProjetoRepository.findTeamByProjetoId(id);

        if (usuarioProjetos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioProjetos, HttpStatus.OK);
    }

    @PutMapping("/update/{projetoId}/{usuarioId}")
    public ResponseEntity<Void> updateUsuarioProjeto(@PathVariable Long projetoId, @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioProjetoDTO data) {
        UsuarioProjetoId id = new UsuarioProjetoId(projetoId, usuarioId);
        usuarioProjetoRepository.updateUsuarioProjeto(id, data.projetoId(), data.cargo());
        return ResponseEntity.ok().build();
    }

    // add usuario ao projeto endpoints
    @PostMapping("/add")
    public ResponseEntity<Void> addUsuarioAoProjeto(
            @RequestBody @Valid UsuarioProjetoDTO data) {

        long teste = usuarioProjetoRepository.findUsuarioIdByEmail(data.email());

        usuarioProjetoRepository.insertUsuarioProjeto(teste, data.projetoId(), data.cargo());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/upprojeto/{id}")
    public ResponseEntity<Projeto> updateProjeto(@PathVariable Long id,
            @Validated @RequestBody ProjetoRequestDTO data) {
        Optional<Projeto> optionalProjeto = projetoRepository.findById(id);

        if (!optionalProjeto.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Projeto projeto = optionalProjeto.get();
        projeto.setNome(data.nome());
        projeto.setDescricao(data.descricao());
        projeto.setDataInicio(data.dataInicio());
        projeto.setDataFim(data.dataFim());

        Projeto updatedProjeto = projetoRepository.save(projeto);
        return ResponseEntity.ok(updatedProjeto);
    }

    // Remover um usuário de um projeto:
    @DeleteMapping("/{projetoId}/usuario/{usuarioId}")
    public ResponseEntity<Void> removeUsuarioFromProjeto(@PathVariable Long projetoId, @PathVariable Long usuarioId) {
        UsuarioProjetoId id = new UsuarioProjetoId(projetoId, usuarioId);
        if (usuarioProjetoRepository.existsById(id)) {
            List<Tarefa> tarefas = tarefaRepository.findTarefasByUsuarioIdAndProjetoId(usuarioId, projetoId);
            if (tarefas.isEmpty()) {
                usuarioProjetoRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Long> getUserIdByEmail(@PathVariable String email) {
        Optional<Long> usuario = usuarioRepository.findUserIdByEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
// // Obter todos os usuários de um projeto:
// @GetMapping("/{projetoId}/usuarios")
// public ResponseEntity<List<UsuarioProjetoResponseDTO>>
// getUsuariosByProjetoId(@PathVariable Long projetoId) {
// List<UsuarioProjeto> usuarioProjetos =
// usuarioProjetoRepository.findTeamByProjetoId(projetoId);
// List<UsuarioProjetoResponseDTO> response = usuarioProjetos.stream()

// .map(UsuarioProjetoResponseDTO::new)
// .collect(Collectors.toList());
// return ResponseEntity.ok(response);
// }
