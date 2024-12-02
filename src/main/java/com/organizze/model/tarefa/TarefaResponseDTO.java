package com.organizze.model.tarefa;

import java.util.Date;

public record TarefaResponseDTO(
        Long id,
        String nome,
        String observacoes,
        Date dataCriacao,
        Date dataEntrega,
        Long projetoId,
        Long statusId,
        Long usuarioId) {
    public TarefaResponseDTO(Tarefa tarefa) {
        this(
            tarefa.getId(),
            tarefa.getNome(),
            tarefa.getObservacoes(),
            tarefa.getDataCriacao(),
            tarefa.getDataEntrega(),
            tarefa.getProjetoId(),
            tarefa.getStatusId(),
            tarefa.getUsuarioId()
        );
    }
}
