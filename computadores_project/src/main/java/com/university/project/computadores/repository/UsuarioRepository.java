package com.university.project.computadores.repository;

import com.university.project.computadores.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas à entidade Usuario.
 * Estende JpaRepository para herdar métodos básicos de CRUD.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca um usuário pelo nome de usuário (username).
     * @param username Nome de usuário a ser buscado
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<Usuario> findByUsername(String username);
}
