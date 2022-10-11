package org.models;

import org.persistence.BaseRepository;

import java.util.UUID;

public class BaseEntity {

    public UUID id;

    public BaseEntity() {}
    public BaseEntity(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
