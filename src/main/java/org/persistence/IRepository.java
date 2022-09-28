package org.persistence;

import org.models.BaseEntity;

import java.util.List;

public interface IRepository <T extends BaseEntity> {

    T add(T entity);
    T update(T entity);
    T get(T entity);
    List<T> get();
    void delete(T entity);
}
