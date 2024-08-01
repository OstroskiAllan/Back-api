package com.organizze.model.usuario_projeto;

public record UsuarioProjetoRegisterDTO(
    Long usuarioId,
    Long projetoId,
    String cargo) {
}