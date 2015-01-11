package app.ejb;

import app.model.Work;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
@Stateless(name = "WorkFacadeEJB")
public class WorkFacadeBean extends DataFacade {
    @PersistenceContext(unitName = "FreeArt")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public WorkFacadeBean() {
        super(Work.class);
    }

    @Override
    public List<Work> findAny(String search) {
        search = "%" + search + "%";

        CriteriaBuilder criteriaBuilder = getEntityManager()
                .getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Work.class);

        Root<Work> root = criteriaQuery.from(Work.class);
        ParameterExpression<String> searchParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(root)
                .where(
                    criteriaBuilder.or(
                        criteriaBuilder.like(root.<String>get("title"), searchParameter),
                        criteriaBuilder.like(root.<String>get("description"), searchParameter),
                        criteriaBuilder.like(root.<String>get("file"), searchParameter),
                        criteriaBuilder.like(root.<String>get("location"), searchParameter),
                        criteriaBuilder.like(root.<String>get("title"), searchParameter)
                    )
                );

        Query query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(searchParameter, search);

        return query.getResultList();
    }
}
