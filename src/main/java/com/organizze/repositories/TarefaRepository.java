package com.organizze.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.organizze.model.tarefa.Tarefa;
import com.organizze.model.projeto.Projeto;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

     @Query(value = "SELECT * FROM Tarefa WHERE usuario_projeto_projeto_id = :projetoId", nativeQuery = true)
    List<Tarefa> findByProjetoId(@Param("projetoId") Long projetoId);

    //List<Tarefa> findByTarefaProjetoId(Long projetoId);
    //List<Tarefa> findByProjetoId(Long projetoId);
}
