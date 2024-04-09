package com.organizze.model.projeto;

import java.sql.Date;

import com.organizze.model.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projeto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;

    private String descricao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_criacao")
    private Date data_criacao;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "data_inicio")
    private Date data_inicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "data_fim")
    private Date data_fim;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Projeto(String nome, String descricao, Date data_inicio, Date data_fim, Long usuarioId){
        this.nome = nome;
        this.descricao = descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.usuario = new Usuario(usuarioId);
    }
    
}


