package com.organizze.repositories;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.organizze.model.usuario_projeto.UsuarioProjeto;
import com.organizze.model.usuario_projeto.UsuarioProjetoId;

public interface UsuarioProjetoRepository extends JpaRepository<UsuarioProjeto, UsuarioProjetoId>{
    List<UsuarioProjeto> findByUsuarioId(Long usuarioId);
}
