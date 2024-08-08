package com.organizze.model.projeto;

import java.sql.Date;


public record ProjetoRegisterDTO(String nome, String descricao, Date dataInicio, Date dataFim){
}