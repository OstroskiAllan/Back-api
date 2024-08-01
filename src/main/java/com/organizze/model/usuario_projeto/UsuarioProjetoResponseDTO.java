package com.organizze.model.usuario_projeto;

public record UsuarioProjetoResponseDTO(
        Long usuarioId,
        Long projetoId,
        String cargo) {

    public UsuarioProjetoResponseDTO(UsuarioProjeto usuarioProjeto) {
        this(
                usuarioProjeto.getUsuarioId(),
                usuarioProjeto.getProjetoId(),
                usuarioProjeto.getCargo());

    }
}
