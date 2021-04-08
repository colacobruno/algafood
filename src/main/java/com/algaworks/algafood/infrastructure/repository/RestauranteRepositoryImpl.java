package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;

/*
 Estendendo RestauranteRepositoryQueries pois se mudar o nome da consulta (find) aqui o no controller, não dará  erro
 em tempo de compilação e ficará mais difícil de achar o erro.

 Se aqui, nesta classe, estendermos o repositorio RestauranteRepository, iríamos ter que implementar diversos outros
 métodos que não fazem sentido importar. Para isso, o modo mais correto é refatorar a classe RestauranteRepositoryImpl
 criando uma interface e estendendo ela dessa interface criada. Dessa forma, podemos adicionar essa nova interface
 criada RestauranteRepositoryQueries na classe RestauranteRepository e nesta última, não iremos precisar declarar
 esse novo método criado aqui, pois como estende a nova interface, já contempla esse método. Assim, se mudarmos
 o nome do métod find para qualquer outro, irá dar erro de compilação no projeto.
 */
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        // era para usar var ao invés de String, mas é do java 10
        String jpql = "from Restaurante where nome like :nome " +
                "and taxaFrete between :taxaInicial and :taxaFinal";

        return entityManager.createQuery(jpql, Restaurante.class)
                .setParameter("nome","%" + nome + "%")
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("taxaFinal", taxaFreteFinal)
                .getResultList();
    }
}
