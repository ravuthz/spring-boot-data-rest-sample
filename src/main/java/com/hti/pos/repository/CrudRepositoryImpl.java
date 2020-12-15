package com.hti.pos.repository;

import com.hti.pos.domain.BaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ravuthz
 * Date : 12/5/2020, 12:25 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Service
@Transactional
public class CrudRepositoryImpl<T extends BaseEntity> implements CrudRepository<T, Long> {

    private final EntityManager em;

    private Class<T> entityClass;

    public CrudRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    protected T getInstance() throws Exception {
        return entityClass.newInstance();
    }

    @Override
    public T findOne(Class<T> entityClass, Long id) {
        Assert.notNull(entityClass, "Entity class must not be null!");
        Assert.notNull(id, "Entity's ID must not be null!");
        this.entityClass = entityClass;
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll(Class<T> entityClass) {
        Assert.notNull(entityClass, "Entity class must not be null!");
        return em.createQuery("from " + entityClass.getName(), entityClass).getResultList();
    }

    @Override
    public T save(T entity) {
        Assert.notNull(entity, "Entity must not be null!");
        if (((BaseEntity) entity).getId() != null) {
            em.persist(entity);
        } else {
            entity = em.merge(entity);
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "Entity must not be null!");
        if (em.contains(entity)) {
            em.remove(entity);
        } else {
            em.merge(entity);
        }
    }
}
