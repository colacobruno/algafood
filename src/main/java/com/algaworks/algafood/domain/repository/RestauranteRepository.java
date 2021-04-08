package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {

    // Pode ser query, find, get
    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome,@Param("id") Long cozinhaId);

    // List<Restaurante> findByNomeContainingAndCozinhaId(String nome,Long cozinhaId);

    // Pesquisa e retorna  o primeiro registro encontrado
    // LIKE + limit no sql
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    // Pesquisa e retorna os 2 primeiros registros encontrados
    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);
}
