package com.organizze.model.usuario_projeto;

public record UsuarioProjetoRequestDTO(
    Long usuarioId,
    Long projetoId,
    String cargo ) {
    
}
    