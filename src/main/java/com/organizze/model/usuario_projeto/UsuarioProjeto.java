package com.organizze.model.usuario_projeto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "usuario_projeto")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UsuarioProjetoId.class)
public class UsuarioProjeto {
    @Id
    private Long usuarioId;
    @Id
    private Long projetoId;

    private String cargo;
}
