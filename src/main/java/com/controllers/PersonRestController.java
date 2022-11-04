package com.controllers;

import org.library.*;
import org.models.Person;
import org.persistence.IRepository;

@RestController(path = "person")
public class PersonRestController {

    @AutoWired
    private IRepository<Person> repo;

    public PersonRestController() {}

    @GET
    public String sayHello() {
        return "hello from rest controller";
    }

    @POST
    public Person addPerson(@FromBody(name = "person") Person person) {
        return repo.add(person);
    }
}
