package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Simon on 09/01/2015.
 */
@Stateless(name = "CategoryFacadeEJB")
public class CategoryFacadeBean extends DataFacade {
    @PersistenceContext(name = "FreeArt")
    private EntityManager entityManager;

    public CategoryFacadeBean() {
        super(CategoryFacadeBean.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
