package com.organizze.model.projeto;

import java.util.Date;

public record ProjetoRequestDTO(
    String nome,
    String descricao,
    Date dataCriacao,
    Date dataInicio,
    Date dataFim,
    Integer usuarioId
){
    
}
