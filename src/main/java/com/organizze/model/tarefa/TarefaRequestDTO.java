package com.organizze.model.tarefa;

import java.util.Date;

public record TarefaRequestDTO(
    String nome,
    String observacoes,
    Date dataEntrega,
    Long projetoId,
    Long statusId,
    Long usuarioId
) {
} 