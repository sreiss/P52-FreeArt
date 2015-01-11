package app.ejb;

import app.model.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Andesite on 1/11/2015.
 */
@Stateless(name = "AuthorFacadeEJB")
public class AuthorFacadeBean extends DataFacade {
    @PersistenceContext(name = "FreeArt")
    private EntityManager entityManager;

    public AuthorFacadeBean() {
        super(Author.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List findAny(String search) {
        search = "%" + search + "%";

        CriteriaBuilder criteriaBuilder = getEntityManager()
                .getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Author.class);

        Root<Author> root = criteriaQuery.from(Author.class);
        ParameterExpression<String> searchParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(root)
                .where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.<String>get("login"), searchParameter),
                                criteriaBuilder.like(root.<String>get("name"), searchParameter),
                                criteriaBuilder.like(root.<String>get("firstName"), searchParameter)
                        )
                );

        Query query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(searchParameter, search);

        return query.getResultList();
    }

    public Author findByLogin(String login) {
        CriteriaBuilder criteriaBuilder = getEntityManager()
                .getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Author.class);

        Root<Author> root = criteriaQuery.from(Author.class);
        ParameterExpression<String> searchParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("login"), searchParameter));

        Query query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(searchParameter, login);

        return (Author) query.getSingleResult();
    }
}
