package com.organizze.model.projeto;

import java.sql.Date;

import com.organizze.model.usuario.Usuario;

public record RegisterDTO(String nome, String descricao, Date data_inicio, Date data_fim, Long usuarioId){
}