package se.alten.project.service;

import static org.junit.Assert.*;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.alten.project.AbstractTests;
import se.alten.project.model.ResultDto;
import se.alten.project.model.VisitDto;;

@Transactional
public class VisitServiceTests extends AbstractTests {

    @Autowired
    private VisitService service;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        LOG.info("Setup test");

        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Test
    public void testAddVisitForExistingPerson()
            throws JsonParseException, JsonMappingException, IOException {
        LOG.info("Running first test");
        String jsonString =
                "{"
                + "'check_in' : '2016-03-07T10:25:39.333Z',"
                + "'person_id' : '1'"
                + "}";

        VisitDto visitDto = mapper.readValue(jsonString , VisitDto.class);
        ResultDto resultDto = service.addUnique(visitDto);

        Assert.assertThat(
                resultDto.getStatus(),
                CoreMatchers.is(HttpStatus.OK));

        VisitDto actualVisitDto = (VisitDto) resultDto.getData();
        Assert.assertThat(
                actualVisitDto.getCheckIn(),
                CoreMatchers.equalTo(visitDto.getCheckIn()));
        Assert.assertThat(
                actualVisitDto.getPersonId(),
                CoreMatchers.equalTo(visitDto.getPersonId()));
    }

    @Test
    public void testAddVisitForNewPerson() throws Exception {
        String jsonString =
                "{"
                + "'check_in' : '2016-03-07T10:25:39.333Z',"
                + "'person_id' : '0',"
                + "'person_fullname' : 'Foo Bar'"
                + "}";

        VisitDto visitDto = mapper.readValue(jsonString , VisitDto.class);

        LOG.debug("PERSON FULLNAME " + visitDto.getPersonFullName());

        ResultDto resultDto = service.addUnique(visitDto);

        VisitDto resultVisitDto = (VisitDto) resultDto.getData();
        assertThat(
                resultVisitDto.getPersonId(), CoreMatchers.equalTo(new Long(3)));

        LOG.debug("Hello world");
    }

}
