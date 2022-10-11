package org.models;

import java.util.*;
public class Person extends BaseEntity{

    private String name;
    private String email;
    private String phone;
    public Person() {}
    public Person(UUID id) {
        super(id);
    }

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone() {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "{ " + "id: " + getId() + ", name: " + getName() + ", email: " + getEmail() + ", phone: " + getPhone() + " }";
    }
}
