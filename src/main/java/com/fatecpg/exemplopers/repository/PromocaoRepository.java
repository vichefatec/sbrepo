package com.fatecpg.exemplopers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatecpg.exemplopers.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
    Optional<Promocao> findByIdAndFuncionarioId(Long id, Long funcionarioId);
}
