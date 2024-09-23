package com.organizze.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.organizze.model.usuario_projeto.*;
import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.tarefa.TarefaRequestDTO;
import com.organizze.model.tarefa.TarefaResponseDTO;
import com.organizze.repositories.ProjetoRepository;
import com.organizze.repositories.TarefaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ProjetoRepository projetoRepository;

    @GetMapping("/{projetoId}")
    public ResponseEntity<List<TarefaResponseDTO>> getTarefasFromProjeto(@PathVariable Long projetoId) {
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);

        List<TarefaResponseDTO> tarefaList = tarefas.stream()
                .map(TarefaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tarefaList);
    }

    // @PostMapping("/")
    // public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaRequestDTO tarefaRequestDTO) {
    //     // Usando java.util.Date para a data atual
    //     Date dataAtual = new Date();

    //     // Criando uma nova tarefa com os dados recebidos
    //     Tarefa novaTarefa = new Tarefa(
    //             tarefaRequestDTO.nome(),
    //             tarefaRequestDTO.observacoes(),
    //             dataAtual, // Definindo a data de criação como o momento atual
    //             tarefaRequestDTO.dataEntrega(),
    //             tarefaRequestDTO.projetoId(),
    //             tarefaRequestDTO.statusId(),
    //             tarefaRequestDTO.usuarioId());

    //     // Definindo os valores para as colunas da tabela usuario_projeto
    //     novaTarefa.setUsuarioProjetoProjetoId(tarefaRequestDTO.projetoId()); // Definir o ID do projeto
    //     novaTarefa.setUsuarioProjetoUsuarioId(tarefaRequestDTO.usuarioId()); // Definir o ID do usuário
    //     novaTarefa.setUsuarioProjetoDataInicioTrabalho(new Date(System.currentTimeMillis())); // Definir a data de
    //                                                                                           // início de trabalho

    //     // Salvando a nova tarefa no repositório
    //     tarefaRepository.save(novaTarefa);

    //     // Criando a resposta com os dados da tarefa criada
    //     TarefaResponseDTO resposta = new TarefaResponseDTO(novaTarefa);

    //     // Retornando a resposta com o status HTTP 201 Created
    //     return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    // }

}
