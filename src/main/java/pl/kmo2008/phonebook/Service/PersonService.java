package pl.kmo2008.phonebook.Service;

import pl.kmo2008.phonebook.Entity.Person;

import java.util.stream.Stream;

/**
 * Service for database
 */
public interface PersonService {

    Person save(Person person);

    void delete(Person person);


    Iterable<Person> findAll();

    void deleteAllByNameContains(String name);
}