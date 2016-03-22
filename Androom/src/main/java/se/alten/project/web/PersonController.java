package se.alten.project.web;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.alten.project.domain.Person;
import se.alten.project.model.ResultDto;
import se.alten.project.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResultDto add(@RequestBody Person person) {
        ResultDto resultDto = personService.add(person);
        return resultDto;
    }

    @RequestMapping(value="/getAll", method=RequestMethod.GET)
    public Collection<Person> getAll() {
        Collection<Person> all = personService.findAll();

        return all;
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ResultDto getPersonByFullName(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        Person result = personService.findByFullName(
                firstName, lastName);

        if (result != null)
            return new ResultDto.Builder()
                    .status(HttpStatus.OK)
                    .data(result)
                    .build();
        return new ResultDto.Builder()
                .status(HttpStatus.NOT_FOUND)
                .message("No person found")
                .build();
    }

    @RequestMapping(value="/test", method=RequestMethod.GET)
    public void test() {
        Person person = personService.getById(new Long(1));
        LOG.debug(person.getFirstName());
        LOG.debug(String.format(
                "Number of visits=%d", person.getVisits().size()));
    }
}
