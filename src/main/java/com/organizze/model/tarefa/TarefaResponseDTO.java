package com.organizze.model.tarefa;

import java.sql.Date;

public record TarefaResponseDTO(
        Long id,
        String nome,
        String observacoes,
        Date dataEntrega,
        Long projetoId,
        Long statusId,
        Long usuarioId) {
    public TarefaResponseDTO(Tarefa tarefa) {
        this(
            tarefa.getId(),
            tarefa.getNome(),
            tarefa.getObservacoes(),
            tarefa.getDataEntrega(),
            tarefa.getProjetoId(),
            tarefa.getStatusId(),
            tarefa.getUsuarioId()
            );
    }
}
