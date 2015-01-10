package app.ejb;

import app.model.Work;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return new ArrayList<Work>();
    }
}
