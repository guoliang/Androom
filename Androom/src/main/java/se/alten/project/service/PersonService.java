package se.alten.project.service;

import java.util.Collection;

import se.alten.project.domain.Person;
import se.alten.project.model.ResultDto;

public interface PersonService {

    public Collection<Person> findAll();

    public ResultDto add(Person person);

    public Person getById(Long id);

    public Person findByFullName(String firstName, String lastName);
}
