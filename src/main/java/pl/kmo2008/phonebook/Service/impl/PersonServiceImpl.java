package pl.kmo2008.phonebook.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kmo2008.phonebook.Entity.Person;
import pl.kmo2008.phonebook.Repo.PersonRepository;
import pl.kmo2008.phonebook.Service.PersonService;

import javax.transaction.Transactional;
import java.util.stream.Stream;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public void setBookRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }


    @Override
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void deleteAllByNameContains(String name) {
        personRepository.deleteAllByNameContains(name);
    }


}
