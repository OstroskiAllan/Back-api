package com.organizze.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.projeto.Projeto;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
    //List<Tarefa> findByTarefaProjetoId(Long projetoId);
    List<Tarefa> findByProjetoId(Long projetoId);
}
