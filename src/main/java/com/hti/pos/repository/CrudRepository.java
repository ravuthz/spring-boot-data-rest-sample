package com.hti.pos.repository;

import com.hti.pos.domain.BaseEntity;

import java.util.List;

/**
 * Created by ravuthz
 * Date : 12/5/2020, 12:23 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

public interface CrudRepository<T extends BaseEntity, ID> {
    T findOne(Class<T> entityClass, Long id);

    List<T> findAll(Class<T> entityClass);

    T save(T entity);

    void delete(T entity);
}
