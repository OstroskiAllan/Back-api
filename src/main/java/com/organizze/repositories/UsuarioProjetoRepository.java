package com.organizze.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.organizze.model.usuario_projeto.UsuarioProjeto;
import com.organizze.model.usuario_projeto.UsuarioProjetoId;

public interface UsuarioProjetoRepository extends JpaRepository<UsuarioProjeto, UsuarioProjetoId> {
    
    List<UsuarioProjeto> findByUsuarioId(Long usuarioId);

    @Query("SELECT up FROM UsuarioProjeto up WHERE up.usuarioId = :usuarioId AND up.cargo <> 'Gerente'")
    List<UsuarioProjeto> findByUsuarioIdAndNotGerente(@Param("usuarioId") Long usuarioId);

    @Query("SELECT up FROM UsuarioProjeto up WHERE up.projetoId = :projetoId")
    List<UsuarioProjeto> findTeamByProjetoId(@Param("projetoId") Long projetoId);

}
