package org.persistence;

import org.models.BaseEntity;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository <T extends BaseEntity> {

    T add(T entity);
    T update(T entity);
    T get(T entity);
    List<T> get();
    void delete(T entity);
    T get(Predicate<T> expression);
    List<T> filter(Predicate<T> expression);
}
