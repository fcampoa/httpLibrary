package org.controllers;

import org.models.Person;
import org.persistence.IRepository;
import org.server.HttpController;
import org.util.HttpStatus;
import org.util.Request;
import org.util.Response;
import org.util.Utilities;

import java.util.UUID;

public class PersonController extends HttpController {
    private IRepository<Person> personRepo;

    public PersonController(IRepository<Person> personRepo) {
        this.personRepo = personRepo;
    }
    @Override
    protected Response get(Request request) {
        return new Response(personRepo.get(), HttpStatus.OK.getValue());
    }
    @Override
    protected Response post(Request request) {
        try {
            Person p = Utilities.<Person>fromJson(request.getBody().toString(), Person.class);
            return new Response(personRepo.add(p), HttpStatus.OK.getValue());
        }
         catch(Exception ex) {
            ex.printStackTrace();
         }
        return null;
        }
    @Override
    protected Response put(Request request) {
        Person p = (Person) request.getBody();
        return new Response(personRepo.update(p), HttpStatus.OK.getValue());
    }
    @Override
    protected Response patch(Request request) {
        Person p = (Person) request.getBody();
        return new Response(personRepo.update(p), HttpStatus.OK.getValue());
    }

    @Override
    protected Response delete(Object id) {
        return new Response("Deleted", HttpStatus.OK.getValue());
    }

    @Override
    protected Response get(Object id) {
        return new Response(personRepo.get(p -> p.getId().equals(id)), HttpStatus.OK.getValue());
    }
}
