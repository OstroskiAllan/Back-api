package com.organizze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organizze.model.projeto.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer>{
}