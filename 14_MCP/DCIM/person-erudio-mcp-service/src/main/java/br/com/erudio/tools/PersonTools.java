package br.com.erudio.tools;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonTools {

    private PersonRepository personRepository;

    public PersonTools(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Tool(name = "getPersonById", description = "Find person by ID")
    public Person getPersonById(
            @ToolParam(description = "Person ID") Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Tool(name = "getPersonsByNationality", description = "Find all persons by nationality")
    public List<Person> getPersonsByNationality(
            @ToolParam(description = "Nationality") String nationality) {
        return personRepository.findByNationality(nationality);
    }

}
