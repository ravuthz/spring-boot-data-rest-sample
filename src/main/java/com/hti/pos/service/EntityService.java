package com.hti.pos.service;

import com.hti.pos.repository.EntityRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntityService<T> {
    private final EntityManager entityManager;

    public EntityService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityRepository getRepository(Class<T> entityClass) {
        return new EntityRepository(entityClass, entityManager);
    }

    public List<T> findAll(Class<T> entityClass) {
        return getRepository(entityClass).findAll();
    }

    public List<T> findAll(Class<T> entityClass, Sort sort) {
        return getRepository(entityClass).findAll(sort);
    }

    public List<T> findAllById(Class<T> entityClass, Iterable<Long> ids) {
        return getRepository(entityClass).findAllById(ids);
    }

    public <S extends T> List<S> saveAll(Class<T> entityClass, Iterable<S> entities) {
        return getRepository(entityClass).saveAll(entities);
    }

    public void flush(Class<T> entityClass) {
        getRepository(entityClass).flush();
    }

    public <S extends T> S saveAndFlush(Class<T> entityClass, S entity) {
        return (S) getRepository(entityClass).saveAndFlush(entity);
    }

    public void deleteInBatch(Class<T> entityClass, Iterable<T> entities) {
        getRepository(entityClass).deleteInBatch(entities);
    }

    public void deleteAllInBatch(Class<T> entityClass) {
        getRepository(entityClass).deleteAllInBatch();
    }

    public T getOne(Class<T> entityClass, Long id) {
        return (T) getRepository(entityClass).getOne(id);
    }

    public <S extends T> List<S> findAll(Class<T> entityClass, Example<S> example) {
        return getRepository(entityClass).findAll(example);
    }

    public <S extends T> List<S> findAll(Class<T> entityClass, Example<S> example, Sort sort) {
        return getRepository(entityClass).findAll(example, sort);
    }

    public Page<T> findAll(Class<T> entityClass, Pageable pageable) {
        return getRepository(entityClass).findAll(pageable);
    }

    public <S extends T> S save(Class<T> entityClass, S entity) {
        return (S) getRepository(entityClass).save(entity);
    }

    public Optional<T> findById(Class<T> entityClass, Long id) {
        return getRepository(entityClass).findById(id);
    }

    public boolean existsById(Class<T> entityClass, Long id) {
        return getRepository(entityClass).existsById(id);
    }

    public long count(Class<T> entityClass) {
        return getRepository(entityClass).count();
    }

    public void deleteById(Class<T> entityClass, Long id) {
        getRepository(entityClass).deleteById(id);
    }

    public void delete(Class<T> entityClass, T entity) {
        getRepository(entityClass).delete(entity);
    }

    public void deleteAll(Class<T> entityClass, Iterable<? extends T> entities) {
        getRepository(entityClass).deleteAll(entities);
    }

    public void deleteAll(Class<T> entityClass) {
        getRepository(entityClass).deleteAll();
    }

    /* Custom Repository methods */

    public Optional<T> findByField(Class<T> entityClass, String field, Object value) {
        return getRepository(entityClass).findOne((root, query, builder) -> builder.equal(root.get(field), value));
    }
}
