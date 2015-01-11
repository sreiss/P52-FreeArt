package app.ejb;

import org.eclipse.persistence.internal.jpa.querydef.ParameterExpressionImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simon on 09/01/2015.
 */
public abstract class DataFacade<T> {
    private Class<T> entityClass;

    public DataFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T find(int id) {
        return getEntityManager().find(entityClass, id);
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager()
                .getCriteriaBuilder()
                .createQuery();

        cq.select(cq.from(entityClass));

        return getEntityManager().createQuery(cq)
                .getResultList();
    }

    public abstract List<T> findAny(String search);

    public int count(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager()
                .getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root<T> root = criteriaQuery.from(entityClass);
        ParameterExpression<Integer> entityId = criteriaBuilder.parameter(Integer.class);

        criteriaQuery.select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(root.get("id"), entityId));

        Query query = getEntityManager().createQuery(criteriaQuery);
            query.setParameter(entityId, id);

        return ((Long) query.getSingleResult()).intValue();
    }

    public int count() {
        CriteriaQuery criteriaQuery = getEntityManager()
                .getCriteriaBuilder()
                .createQuery();

        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(getEntityManager().getCriteriaBuilder().count(root));
        Query query = getEntityManager().createQuery(criteriaQuery);

        return ((Long) query.getSingleResult()).intValue();
    }
}
