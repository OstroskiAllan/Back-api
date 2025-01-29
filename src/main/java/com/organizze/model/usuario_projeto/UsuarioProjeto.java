package com.organizze.model.usuario_projeto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "usuario_projeto")
@Entity
@Getter
@NoArgsConstructor


@IdClass(UsuarioProjetoId.class)
public class UsuarioProjeto {
    @Id
    private Long usuarioId;
    @Id
    private Long projetoId;

    private String cargo;
    
    // Constructors
    public UsuarioProjeto(Long usuarioId, Long projetoId, String cargo) {
        this.usuarioId = usuarioId;
        this.projetoId = projetoId;
        this.cargo = cargo;
    }

     // Getters e setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
