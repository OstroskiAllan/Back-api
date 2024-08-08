package com.organizze.model.projeto;

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
@Table(name = "projeto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descricao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_criacao")
    private Date data_criacao;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "data_inicio")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name = "data_fim")
    private Date dataFim;

    public Projeto(String nome, String descricao, Date dataInicio, Date dataFim){
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}


