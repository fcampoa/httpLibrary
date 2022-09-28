package org.models;

import java.util.UUID;

public class BaseEntity {

    protected UUID id;

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
