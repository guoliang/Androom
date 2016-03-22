package se.alten.project.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import se.alten.project.domain.Person;
import se.alten.project.domain.PersonRepository;
import se.alten.project.domain.Visit;
import se.alten.project.domain.VisitRepository;
import se.alten.project.library.Helper;
import se.alten.project.model.ResultDto;
import se.alten.project.model.ResultDto.Builder;
import se.alten.project.model.VisitDto;

@Service
public class VisitServiceBean implements VisitService {

    Logger LOG = LoggerFactory.getLogger(VisitServiceBean.class);
    private static final HttpStatus OK_STATUS = HttpStatus.OK;
    private static final HttpStatus NOK_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public ResultDto addUnique(VisitDto visitDto) {

        long personId = visitDto.getPersonId();
        String personFullName = visitDto.getPersonFullName();

        if (personId == 0) {
            Person person = new Person(personFullName);
            person = personRepository.save(person);
            visitDto.setPersonId(person.getId());
            return addNewVisit(visitDto, person);
        }

        LOG.debug(String.format("Check if personId=%d already visiting", personId));
        Collection<Visit> personCurrentVisit = getByIdPersonCurrentVisit(personId);
        boolean personVisiting = !personCurrentVisit.isEmpty();

        if (personVisiting) {
            return new ResultDto.Builder()
                    .status(NOK_STATUS)
                    .message("Person is already visiting")
                    .build();
        }

        LOG.debug(String.format("Adding visit to personId=%d", personId));
        Person person = entityManager.getReference(Person.class, personId);
        return addNewVisit(visitDto, person);
    }

    private ResultDto addNewVisit(VisitDto visitDto, Person person) {
        try {

            Date parsedDate;
            parsedDate = Helper.parseStringToDate(visitDto.getCheckIn());
            Visit newVisit = new Visit(parsedDate, person);
            LOG.debug(String.format("New visitID=%d", newVisit.getId()));

            Visit savedVisit = visitRepository.save(newVisit);
            LOG.debug(String.format("Saved visitID=%d", savedVisit.getId()));

            boolean resultSaved = savedVisit.getId() > 0;

            if (!resultSaved)
                return new ResultDto.Builder()
                        .status(NOK_STATUS)
                        .error("Failed to save a new entry of Visit")
                        .build();

            return new ResultDto.Builder()
                    .status(OK_STATUS)
                    .data(visitDto)
                    .build();

        } catch (ParseException e) {
            e.printStackTrace();
            return new ResultDto.Builder()
                    .status(NOK_STATUS)
                    .error(e.getMessage())
                    .build();
        }
    }

    private Collection<Visit> getByIdPersonCurrentVisit(long personId) {
        Collection<Visit> byCheckOut = visitRepository.getByCheckOut(null);
        List<Visit> filteredCollection = byCheckOut.stream()
            .filter(visit -> visit.getPerson().getId().equals(personId))
            .collect(Collectors.toList());

        return filteredCollection;
    }

    @Override
    public ResultDto checkOut(VisitDto visitDto) {

        Long personId = visitDto.getPersonId();

        boolean personVisiting =
                visitRepository.isPersonVisiting(personId);

        Builder resultDtoBuilder = new ResultDto.Builder();

        if (personVisiting) {
            visitRepository.update(personId);
            resultDtoBuilder.message("Person checked out");
        } else {
            resultDtoBuilder.message("Person is not visiting");
        }

        return resultDtoBuilder
                .status(OK_STATUS)
                .build();
    }

    @Override
    public ResultDto checkOut(List<Long> personIds) {

        visitRepository.update(personIds);
        return null;
    }

    @Override
    public ResultDto getVisitsByPersonId(Long personId) {
        Collection<Visit> collection =
                visitRepository.getByPersonId(personId);
        Collection<VisitDto> collectionOfPersonVisits =
                mapPersonVisits(collection);
        return new ResultDto.Builder()
                .data(collectionOfPersonVisits)
                .status(OK_STATUS)
                .build();
    }

    private Collection<VisitDto> mapPersonVisits(Collection<Visit> collection) {

        Collection<VisitDto> mapped = new ArrayList<>();
        for(Visit visit : collection) {
            mapped.add(new VisitDto(
                    visit.getPerson().getId(),
                    String.format("%s %s",
                            visit.getPerson().getFirstName(),
                            visit.getPerson().getLastName()),
                    visit.getCheckIn().toString(),
                    visit.getCheckOut() != null ? visit.getCheckOut().toString() : null));
        }
        return mapped;
    }

    @Override
    public ResultDto getCurrentVisitors() {

        Collection<Visit> currentVisitors =
                visitRepository.getCurrentVisitors();

        Collection<VisitDto> mapPersonVisits = mapPersonVisits(currentVisitors);
        return new ResultDto.Builder()
                .data(mapPersonVisits)
                .status(OK_STATUS)
                .build();
    }

    @Override
    public ResultDto getPastVisitors() {
        Collection<Visit> collection =
                visitRepository.findAll().stream()
                .filter(visit-> visit.getCheckOut() != null)
                .sorted(comparingCheckout().reversed())
                .collect(Collectors.toList());

        Collection<VisitDto> pastVisitors = mapPersonVisits(collection);

        return new ResultDto.Builder()
                .data(pastVisitors)
                .status(OK_STATUS)
                .build();
    }

    private Comparator<Visit> comparingCheckout() {
        return (p1, p2) -> p1.getCheckOut().compareTo(p2.getCheckOut());
    }

}
