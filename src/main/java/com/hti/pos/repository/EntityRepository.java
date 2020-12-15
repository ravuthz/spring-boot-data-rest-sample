package com.hti.pos.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class EntityRepository<T> extends SimpleJpaRepository<T, Long> implements PagingAndSortingRepository<T, Long> {
    public EntityRepository(Class<T> entityClass, EntityManager em) {
        super(entityClass, em);
    }

    public EntityRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityManager em) {
        super(entityInformation, entityManager);
    }


}
