package dao;
// Generated 8 mai 2024, 18:53:53 by Hibernate Tools 4.3.6.Final

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 * Home object for domain model class Compte.
 * @see dao.Compte
 * @author Hibernate Tools
 */
@Component
@Transactional
public class CompteHome {

	private static final Log log = LogFactory.getLog(CompteHome.class);

	@PersistenceContext
	private EntityManager entityManager;
	public static final SessionFactory session=HibernateUtil.getSessionFactory();
	public Compte rechercheCompte(Utilisateur u) {

        try {
        	Session ss=session.openSession();
			Transaction tx=ss.beginTransaction();
            Query q = ss.createQuery("SELECT p FROM Compte p WHERE p.utilisateur = :login ", Compte.class);
            
            q.setParameter("login", u);
            return (Compte) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(); 
        } 
        return null; //Gérez le cas où aucune personne n'est trouvée
    }
	public void persist(Compte transientInstance) {
		log.debug("persisting Compte instance");
		try {Session ss=session.openSession();
		Transaction tx=ss.beginTransaction();
		ss.save(transientInstance);
		tx.commit();
		ss.close();
		log.debug("persist candidat successful");
		//ss.close();
	} catch (RuntimeException re) {
		log.error("persist candidat failed", re);
		throw re;
	}

	}
	

	public void remove(Compte persistentInstance) {
		log.debug("removing Compte instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}
	public void mergee(Compte detachedInstance) {
	    log.debug("merging Compte instance");
	    try {
	        Session s = session.openSession();
	        Transaction tx = s.beginTransaction();
	        s.merge(detachedInstance);
	        tx.commit();
	        session.close();
	        log.debug("merge successful");
	    } catch (RuntimeException re) {
	        log.error("merge failed", re);
	        throw re;
	    }
	}
	public void merge(Compte detachedInstance) {
		log.debug("merging Client instance");
		try {Session ss=session.openSession();
		Transaction tx=ss.beginTransaction();
		ss.merge(detachedInstance);
		tx.commit();
		ss.close();
		log.debug("update Agentcomptable successful");
		//ss.close();
	} catch (RuntimeException re) {
		log.error("update Agentcomptable failed", re);
		throw re;
	}
	}

	public Compte findById(int id) {
		log.debug("getting Compte instance with id: " + id);
		try {
			Compte instance = entityManager.find(Compte.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
