package org.persistence;

import org.models.BaseEntity;

import java.util.*;

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
        return null;
    }

    @Override
    public T get(T entity) {
        return list.stream().filter((T e) ->  e.getId().equals(entity.getId())).findAny().get();
    }

    @Override
    public List<T> get() {
        return list;
    }

    @Override
    public void delete(T entity) {
    list.remove(entity);
    }
}
