package org.persistence;

import org.models.BaseEntity;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseRepository<T extends BaseEntity> implements IRepository<T> {
    protected List<T> list;

    public BaseRepository(List<T> list) {
        this.list = list;
    }
    @Override
    public T add(T entity) {
        entity.setId(UUID.randomUUID());
        list.add(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        int index;
        if ((index = list.indexOf(entity)) >= 0) {
            list.set(index, entity);
            return entity;
        }
        return null;
    }

    @Override
    public T get(T entity) {
        return list.stream().filter((T e) -> e.getId().equals(entity.getId())).findAny().get();
    }

    @Override
    public List<T> get() {
        return list;
    }

    @Override
    public void delete(T entity) {
    list.remove(entity);
    }

    @Override
    public T get(Predicate<T> expression) {
        return list.stream().filter(expression).findAny().get();
    }

    @Override
    public List<T> filter(Predicate<T> expression) {
        return list.stream().filter(expression).collect(Collectors.toList());
    }
}
