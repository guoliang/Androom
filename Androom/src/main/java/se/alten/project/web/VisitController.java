package se.alten.project.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.alten.project.model.ResultDto;
import se.alten.project.model.VisitDto;
import se.alten.project.service.VisitService;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

    private Logger LOG = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private VisitService visitService;

    @RequestMapping(value="", method=RequestMethod.POST)
    public ResultDto checkIn(@RequestBody VisitDto visitDto) {

        ResultDto resultDto = visitService.addUnique(visitDto);

        LOG.debug(String.format(
                "Result for adding Visit to personId=%d, Result=%s",
                visitDto.getPersonId(), resultDto.getStatus()));

        return resultDto;
    }

//    @RequestMapping(value="", method=RequestMethod.PUT)
//    public VisitDto checkOut(@RequestBody VisitDto visitDto) {
//
//        ResultDto result = visitService.checkOut(visitDto);
//
//        visitDto.setResult(result);
//
//        return visitDto;
//    }

    @RequestMapping(value="", method=RequestMethod.PUT)
    public VisitDto checkOutd(@RequestBody VisitDto visitDto) {
        List<Long> personIds = visitDto.getPersonIds();
        personIds.forEach(personId ->
            LOG.debug(String.format("PersonID to remove: %d", personId)));
        visitService.checkOut(personIds);
        return new VisitDto() {{
            setResult(new ResultDto.Builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Checked out %d visitors", personIds.size()))
                    .build());
        }};
    }

    @RequestMapping(
            value="{personId}",
            method=RequestMethod.GET)
    public ResultDto getVisitsByPersonId(@PathVariable Long personId) {

        ResultDto resultDto =
                visitService.getVisitsByPersonId(personId);

        return resultDto;
    }

    @RequestMapping(value="current")
    public ResultDto getCurrentVisitors() {

        ResultDto resultDto = visitService.getCurrentVisitors();

        return resultDto;
    }

    @RequestMapping(value="past")
    public ResultDto getPastVisitors() {

        ResultDto resultDto = visitService.getPastVisitors();

        return resultDto;
    }
}
