package se.alten.project.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import se.alten.project.domain.Person;
import se.alten.project.domain.PersonRepository;
import se.alten.project.model.ResultDto;

@Service
public class PersonServiceBean implements PersonService {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Collection<Person> findAll() {
        List<Person> persons = personRepository.findAll();
        return persons;
    }

    @Override
    public ResultDto add(Person person) {
        Person saved = null;

        if (!person.getFirstName().isEmpty() &&
                !person.getLastName().isEmpty()) {
            try {
                saved = personRepository.save(person);
                return new ResultDto.Builder()
                        .data(saved)
                        .status(HttpStatus.OK)
                        .build();
            } catch (Exception ex) {
                return new ResultDto.Builder()
                        .error("Duplicate not allowed")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }
        return new ResultDto.Builder()
                .message("Cannot add")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Person getById(Long id) {
        Person person = personRepository.findById(id);
        return person;
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        String sqlQuery = String.format("select * from person "
                + "where first_name = '%s' and last_name = '%s'",
                firstName, lastName);
        List<?> resultList = entityManager
            .createNativeQuery(sqlQuery, Person.class)
            .getResultList();

        if (resultList.size() > 0)
            return (Person) resultList.get(0);
        return null;

    }
}
