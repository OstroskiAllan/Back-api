package com.organizze.model.tarefa;


import java.util.Date;
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
import lombok.Setter;

@Entity
@Table(name = "Tarefa")
@Getter
@Setter
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

    @Column(name = "usuario_projeto_projeto_id")
    private Long projetoId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "usuario_projeto_usuario_id")
    private Long usuarioId;
    
    @Column(name = "usuario_projeto_data_inicio_trabalho")
    private Date dataUsuarioInicioTrabalho;

    // Novo construtor que recebe os parâmetros necessários
    public Tarefa(String nome, String observacoes, Date dataCriacao, Date dataEntrega, Long projetoId, Long statusId, Long usuarioId) {
        this.nome = nome;
        this.observacoes = observacoes;
        this.dataCriacao = dataCriacao;
        this.dataEntrega = dataEntrega;
        this.projetoId = projetoId;
        this.statusId = statusId;
        this.usuarioId = usuarioId;
    }
}
