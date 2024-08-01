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

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ProjetoRepository projetoRepository;

    // @PostMapping("/{projetoId}/tarefa")
    // public ResponseEntity createTarefa(@PathVariable Long projetoId, @RequestBody @Valid TarefaRequestDTO data) {
        
    //     Tarefa novaTarefa = new Tarefa(data.nome(), data.observacoes(), data.dataEntrega(), data.statusId());
    //     this.tarefaRepository.save(novaTarefa);

    //     return ResponseEntity.ok().build();
    // }

    @GetMapping("/tarefa/{tarefaId}")
    public ResponseEntity<TarefaResponseDTO> getTarefaById(@PathVariable Long tarefaId) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(tarefaId);
        return tarefa.map(value -> new ResponseEntity<>(new TarefaResponseDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{projetoId}/tarefa")
    public ResponseEntity<List<TarefaResponseDTO>> getTarefasFromProjeto(@PathVariable Long projetoId) {
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);

        List<TarefaResponseDTO> tarefaList = tarefas.stream().map(TarefaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tarefaList);
    }

    // @PutMapping("/tarefa/{tarefaId}")
    // public ResponseEntity updateTarefa(@PathVariable Long tarefaId, @RequestBody @Valid TarefaRequestDTO data) {
    //     Optional<Tarefa> tarefaOptional = tarefaRepository.findById(tarefaId);
    //     if (!tarefaOptional.isPresent()) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }

    //     Tarefa tarefa = tarefaOptional.get();
    //     tarefa.setNome(data.getNome());
    //     tarefa.setObservacoes(data.getObservacoes());
    //     tarefa.setDataEntrega(data.getDataEntrega());
    //     tarefa.setStatusId(data.getStatusId());
    //     tarefa.setUsuarioId(data.getUsuarioId());
    //     tarefaRepository.save(tarefa);

    //     return ResponseEntity.ok().build();
    // }

    @DeleteMapping("/tarefa/{tarefaId}")
    public ResponseEntity deleteTarefa(@PathVariable Long tarefaId) {
        if (!tarefaRepository.existsById(tarefaId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tarefaRepository.deleteById(tarefaId);

        return ResponseEntity.ok().build();
    }

}