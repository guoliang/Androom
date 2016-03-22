package se.alten.project.domain;

import java.util.Collection;
import java.util.List;

public interface VisitRepositoryCustom {

    /**
     * Returns whether person is visiting
     * @param personId
     * @return
     */
    Boolean isPersonVisiting(Long personId);

    /**
     * Returns whether person is visiting
     * @param personFullName
     * @return
     */
    Boolean isPersonVisiting(String personFullName);

    Boolean update(Long personId);

    Boolean update(List<Long> personIds);

    /**
     * Repository function to retrieve Visit where checkOut equals NULL
     * @return
     */
    Collection<Visit> getCurrentVisitors();
}
