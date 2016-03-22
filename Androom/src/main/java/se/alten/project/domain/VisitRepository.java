package se.alten.project.domain;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends
    JpaRepository<Visit, Long>, VisitRepositoryCustom {

    Collection<Visit> getByPersonId(Long personId);

    Collection<Visit> getByCheckOut(Date checkOut);
}
