package pl.kmo2008.phonebook.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kmo2008.phonebook.Entity.Person;

import java.util.stream.Stream;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {



    void deleteAllByNameContains(String name);
}
