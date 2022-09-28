package org.persistence;

import org.models.Person;
import java.util.List;

public class PersonRepository extends BaseRepository<Person> implements IPersonRepository {

    public PersonRepository(List<Person> list) {
        super(list);
    }
}