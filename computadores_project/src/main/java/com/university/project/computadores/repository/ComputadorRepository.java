package com.university.project.computadores.repository;

import com.university.project.computadores.model.Computador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de banco de dados relacionadas à entidade Computador.
 * Estende JpaRepository para herdar métodos básicos de CRUD.
 */
@Repository
public interface ComputadorRepository extends JpaRepository<Computador, Long> {
    
    /**
     * Busca todos os computadores que não foram deletados logicamente.
     * @return Lista de computadores ativos (não deletados)
     */
    List<Computador> findByIsDeletedIsNull();
}
