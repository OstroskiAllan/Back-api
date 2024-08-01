package com.organizze.model.tarefa;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tarefa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String observacoes;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "data_entrega")
    private Date dataEntrega;

    private Long projetoId;
    private Long statusId;
    private Long usuarioId;

    public Tarefa(String nome, String observacoes, Date dataCriacao, Date dataEntrega){
        this.nome = nome;
        this.observacoes = observacoes;
        this.dataCriacao = dataCriacao;
        this.dataEntrega = dataEntrega;
        
    }
}
