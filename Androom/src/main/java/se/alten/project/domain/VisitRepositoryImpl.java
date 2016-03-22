package se.alten.project.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.alten.project.library.Helper;

@Repository
public class VisitRepositoryImpl implements VisitRepositoryCustom {

    Logger LOG = LoggerFactory.getLogger(VisitRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean isPersonVisiting(Long personId) {

        List<?> resultList = getVisitingEntitiesById(personId);

        return resultList.size() > 0;
    }

    @Override
    public Boolean isPersonVisiting(String personFullName) {

        return getVisitingEntitiesFullName(personFullName).size() > 0;
    }

    @Override
    @Transactional // needs for the entityManager
    public Boolean update(Long personId) {
        List<Visit> resultList = getVisitingEntitiesById(personId);
        if (resultList.size() == 1) {
            Visit visit = resultList.get(0);
            visit.setCheckOut(new Date(System.currentTimeMillis()));

            entityManager.merge(visit);
            return true;
        }
        return false;
    }

    @Override
    @Transactional // needs for the entityManager
    public Boolean update(List<Long> personIds) {
        for(Long personId : personIds) {
            if(!update(personId))
                return false;
        }
        return true;
    }

    private List<Visit> getVisitingEntitiesById(Long personId) {
        String sqlQuery =
                String.format(
                        "select * from visit "
                                + "where person_id =%d and check_out is null",
                                personId);

        return executeVisitQuery(sqlQuery);
    }

    private List<?> getVisitingEntitiesFullName(String personFullName) {
        String[] nameElement = personFullName.split(" ");
        String sqlQuery =
                String.format(
                        "select * from person "
                                + "where person.first_name = '%s' and person.last_name = '%s'",
                                nameElement[0], nameElement[1]);
        Query query = entityManager.createNativeQuery(sqlQuery, Person.class);

        List<?> resultList = query.getResultList();
        if (resultList.size() > 0) {
            Long personId = ((Person) resultList.get(0)).getId();
            return getVisitingEntitiesById(personId);
        }
        return resultList;

    }

    private List<Visit> executeVisitQuery(String sqlQuery) {
        Query query = entityManager.createNativeQuery(sqlQuery, Visit.class);

        return Helper.castList(Visit.class, query.getResultList());
    }

    @Override
    public Collection<Visit> getCurrentVisitors() {
        String sqlQuery = "select * from visit where check_out is null";

        return executeVisitQuery(sqlQuery);
    }
}
