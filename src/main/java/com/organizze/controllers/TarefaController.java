package com.organizze.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.tarefa.TarefaRequestDTO;
import com.organizze.model.tarefa.TarefaResponseDTO;
import com.organizze.repositories.ProjetoRepository;
import com.organizze.repositories.TarefaRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/tarefa")
@CrossOrigin(origins = "http://localhost:4200")
public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ProjetoRepository projetoRepository;

    // @Transactional
    @Transactional
    @GetMapping("/{projetoId}")
    public ResponseEntity<List<TarefaResponseDTO>> getTarefasFromProjeto(@PathVariable Long projetoId) {
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);

        List<TarefaResponseDTO> tarefaList = tarefas.stream()
                .map(TarefaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tarefaList);
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> createTarefa(@RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefa = new Tarefa(
                tarefaRequestDTO.nome(),
                tarefaRequestDTO.observacoes(),
                tarefaRequestDTO.dataCriacao(),
                tarefaRequestDTO.dataEntrega(),
                tarefaRequestDTO.projetoId(),
                tarefaRequestDTO.statusId(),
                tarefaRequestDTO.usuarioId());

        Tarefa savedTarefa = tarefaRepository.save(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TarefaResponseDTO(savedTarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> updateTarefa(@PathVariable Long id,
            @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        Optional<Tarefa> optionalTarefa = tarefaRepository.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setNome(tarefaRequestDTO.nome());
            tarefa.setObservacoes(tarefaRequestDTO.observacoes());
            tarefa.setDataEntrega(tarefaRequestDTO.dataEntrega());
            tarefa.setProjetoId(tarefaRequestDTO.projetoId());
            tarefa.setStatusId(tarefaRequestDTO.statusId());
            tarefa.setUsuarioId(tarefaRequestDTO.usuarioId());
            Tarefa updatedTarefa = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(new TarefaResponseDTO(updatedTarefa));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}