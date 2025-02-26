package com.organizze.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.organizze.model.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    // Optional<Usuario> findByEmail(String email); 
    // @Query("SELECT u.id FROM Usuario u WHERE u.email = :email")
    // Optional<Long> findUserIdByEmail(@Param("email") String email);

    @Query("SELECT u.id FROM usuario u WHERE u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);

    
} 