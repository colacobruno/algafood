package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {


    private EntityManager manager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        String jpql = "from " + getDomainClass().getName(); // retorna o nome da classe da entidade do repositório passado

        T entity =  manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1) // limita no sql para retornar 1 resultado (consulta sql com limit)
                .getSingleResult();

        return Optional.ofNullable(entity); // pode ser nulo ou a própria entidade
    }
}
