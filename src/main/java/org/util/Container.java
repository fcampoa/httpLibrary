package org.util;

import org.models.Person;
import org.persistence.BaseRepository;
import org.persistence.IRepository;

import java.util.ArrayList;

public class Container {

    private static IRepository<Person> personRepo;
    public static IRepository<Person> getPersonRepo() {
        if (personRepo == null) {
            personRepo = new BaseRepository(new ArrayList<>());
        }
        return personRepo;
    }

}
