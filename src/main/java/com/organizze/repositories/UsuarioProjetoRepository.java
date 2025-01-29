package com.organizze.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.organizze.model.usuario_projeto.UsuarioProjeto;
import com.organizze.model.usuario_projeto.UsuarioProjetoId;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioProjetoRepository extends JpaRepository<UsuarioProjeto, UsuarioProjetoId> {

    List<UsuarioProjeto> findByUsuarioId(Long usuarioId);

    @Query("SELECT up FROM UsuarioProjeto up WHERE up.usuarioId = :usuarioId AND up.cargo <> 'Gerente'")
    List<UsuarioProjeto> findByUsuarioIdAndNotGerente(@Param("usuarioId") Long usuarioId);

    @Query("SELECT up FROM UsuarioProjeto up WHERE up.projetoId = :projetoId")
    List<UsuarioProjeto> findTeamByProjetoId(@Param("projetoId") Long projetoId);

    @Query("select u.id from usuario u where u.email = :email")
    Long findUsuarioIdByEmail(@Param("email") String email);

    // Método padrão de salvamento
    default void insertUsuarioProjeto(Long usuarioId, Long projetoId, String cargo) {
        UsuarioProjeto usuarioProjeto = new UsuarioProjeto();
        usuarioProjeto.setUsuarioId(usuarioId);
        usuarioProjeto.setProjetoId(projetoId);
        usuarioProjeto.setCargo(cargo);
        save(usuarioProjeto);
    }

    boolean existsById(UsuarioProjetoId id);

    void deleteById(UsuarioProjetoId id);

    @Transactional
    @Modifying
    @Query("UPDATE UsuarioProjeto u SET u.projetoId = :projetoId, u.cargo = :cargo WHERE u.id = :id")
    void updateUsuarioProjeto(@Param("id") UsuarioProjetoId id, @Param("projetoId") Long projetoId,
            @Param("cargo") String cargo);
}
