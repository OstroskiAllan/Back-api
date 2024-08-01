package com.organizze.model.projeto;

import java.sql.Date;


public record ProjetoRegisterDTO(String nome, String descricao, Date data_inicio, Date data_fim){
}