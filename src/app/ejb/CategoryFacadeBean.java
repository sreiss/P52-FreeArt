package app.ejb;

import app.model.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.ParameterExpression;
import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
@Stateless(name = "CategoryFacadeEJB")
public class CategoryFacadeBean extends DataFacade {
    @PersistenceContext(name = "FreeArt")
    private EntityManager entityManager;

    public CategoryFacadeBean() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Category> findAny(String search) {
        search = "%" + search + "%";
        
        CriteriaBuilder criteriaBuilder = getEntityManager()
                .getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> root = criteriaQuery.from(Category.class);
        ParameterExpression<String> searchParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.like(root.<String>get("name"), searchParameter));

        Query query = getEntityManager().createQuery(criteriaQuery);
            query.setParameter(searchParameter, search);

        return query.getResultList();
    }
}
