package app.ejb;

import app.model.Work;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
