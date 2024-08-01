package com.organizze.model.projeto;

import java.util.Date;

public record ProjetoResponseDTO (
        Long id,
        String nome,
        String descricao,
        Date dataCriacao,
        Date dataInicio,
        Date dataFim
){
    public ProjetoResponseDTO(Projeto projeto){
        this(projeto.getId(),
        projeto.getNome(),
        projeto.getDescricao(),
        projeto.getData_criacao(),
        projeto.getData_inicio(),
        projeto.getData_fim()
        );
    }
}
