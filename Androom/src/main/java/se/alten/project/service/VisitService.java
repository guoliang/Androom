package se.alten.project.service;

import java.util.List;

import se.alten.project.model.ResultDto;
import se.alten.project.model.VisitDto;

public interface VisitService {

    /**
     * Adds a new Visit by person ID, if person not already visiting
     * @param visitDto
     * @return the ResultDto
     */
    ResultDto addUnique(VisitDto visitDto);

    /**
     * Updates Person's Visit entry with checkout time
     * @param visitDto
     * @return the ResultDto
     */
    ResultDto checkOut(VisitDto visitDto);

    ResultDto checkOut(List<Long> personIds);

    ResultDto getVisitsByPersonId(Long personId);

    ResultDto getCurrentVisitors();

    ResultDto getPastVisitors();
}
