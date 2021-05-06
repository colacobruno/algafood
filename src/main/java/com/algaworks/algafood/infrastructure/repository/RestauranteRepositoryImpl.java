package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.ctc.wstx.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comNomeSemelhante;


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

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;


    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){


        CriteriaBuilder builder = entityManager.getCriteriaBuilder(); // Construir elementos para consulta dinâmica

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class); // Criar uma query de restaurante
        Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante

        ArrayList<Predicate> predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0])); // o where espera um array então precisamos converter a lista em array

        TypedQuery<Restaurante> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }

    /* Aula 5.12 */
    /*
    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
            // Era para usar var ao invés de String, mas é do java 10
            StringBuilder jpql = new StringBuilder();
            jpql.append("from Restaurante where 0 = 0 ");

            Map<String, Object> parametros = new HashMap<String, Object>();

            if(StringUtils.hasLength(nome)){
                jpql.append("and LOWER(nome) like :nome ");
                parametros.put("nome", "%" + nome.toLowerCase() + "%");
            }

            if(taxaFreteInicial != null){
                jpql.append("and taxaFrete >= :taxaInicial ");
                parametros.put("taxaInicial", taxaFreteInicial);
            }

            if(taxaFreteFinal != null){
                jpql.append("and taxaFrete <= :taxaFinal ");
                parametros.put("taxaFinal", taxaFreteFinal);
            }

            TypedQuery<Restaurante> query = entityManager
                    .createQuery(jpql.toString(), Restaurante.class);

            parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
        return query.getResultList();
    }
    */

}
