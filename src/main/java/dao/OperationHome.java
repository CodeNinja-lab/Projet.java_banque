package dao;
// Generated 8 mai 2024, 18:53:53 by Hibernate Tools 4.3.6.Final

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 * Home object for domain model class Operation.
 * @see dao.Operation
 * @author Hibernate Tools
 */
@Component

public class OperationHome implements InterMetier
{

	private static final Log log = LogFactory.getLog(OperationHome.class);
	public static final SessionFactory session=HibernateUtil.getSessionFactory();

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Operation transientInstance) {
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

	public void remove(Operation persistentInstance) {
		log.debug("removing Operation instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Operation merge(Operation detachedInstance) {
		log.debug("merging Operation instance");
		try {
			Operation result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Operation findById(int id) {
		log.debug("getting Operation instance with id: " + id);
		try {
			Operation instance = entityManager.find(Operation.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public Compte depot(Compte c, int montant) {

		c.setSolde(montant + c.getSolde());
		return c;
	}

	@Override
	public Compte retrait(Compte c, int montant) {
		// TODO Auto-generated method stub
		c.setSolde(c.getSolde()-montant );
		return c;
	}

	@Override
	public Compte consulter(int numero) {
		// TODO Auto-generated method stub
		 try {
	        	Session ss=session.openSession();
				Transaction tx=ss.beginTransaction();
	            Query q = ss.createQuery("SELECT p FROM Compte p WHERE p.numero = :num ", Compte.class);
	            
	            q.setParameter("num", numero);
	            return (Compte) q.getSingleResult();
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } 
	        return null; //Gérez le cas où aucune personne n'est trouvée
		
	}
}
