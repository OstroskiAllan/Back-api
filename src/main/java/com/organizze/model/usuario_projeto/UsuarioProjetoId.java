package com.organizze.model.usuario_projeto;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioProjetoId implements Serializable {
    private Long projetoId;
    private Long usuarioId;

    // Default constructor
    public UsuarioProjetoId() {}

    // Parameterized constructor
    public UsuarioProjetoId(Long projetoId, Long usuarioId) {
        this.projetoId = projetoId;
        this.usuarioId = usuarioId;
    }

    // Getters and setters
    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioProjetoId that = (UsuarioProjetoId) o;
        return Objects.equals(projetoId, that.projetoId) && Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetoId, usuarioId);
    }
}