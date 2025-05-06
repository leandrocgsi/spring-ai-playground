package br.com.erudio.repository;

import br.com.erudio.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByNationality(String nationality);
}
