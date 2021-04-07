package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {

    // Pode ser query, find, get
    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findByNomeContainingAndCozinhaId(String nome,Long cozinhaId);

    // Pesquisa e retorna  o primeiro registro encontrado
    // LIKE + limit no sql
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    // Pesquisa e retorna os 2 primeiros registros encontrados
    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);
}
