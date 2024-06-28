package dao;
// Generated  8 mai 2024, 18:53:53 by Hibernate Tools 4.3.6.Final
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurHome {

    private static final Log log = LogFactory.getLog(UtilisateurHome.class);
    public static final SessionFactory sessionf = HibernateUtil.getSessionFactory();

    @Autowired
    private SecurityPassword securityPassword;

    @PersistenceContext
    private EntityManager entityManager;

    public Utilisateur connexion(String login, String password) {
        Utilisateur utilisateur = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionf.openSession();
            transaction = session.beginTransaction();
            Query q = session.createQuery("SELECT p FROM Utilisateur p WHERE p.login = :login ", Utilisateur.class);

            q.setParameter("login", login);
            utilisateur = (Utilisateur) q.getSingleResult();

            if (utilisateur != null && securityPassword.verifyPassword(password, utilisateur.getPassword())) {
                // Utilisateur authentifié avec succès
                return utilisateur;
            } else {
                // Mot de passe incorrect
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transaction != null) {
                transaction.commit();
            }
            if (session != null) {
                session.close();
            }
        }

        return null; // Gérez le cas où aucune personne n'est trouvée
    }

    public void persist(Utilisateur transientInstance) {
        log.debug("persisting Utilisateur instance");
        try {
            Session ss = sessionf.openSession();
            Transaction tx = ss.beginTransaction();
            ss.save(transientInstance);
            tx.commit();
            ss.close();
            log.debug("persist candidat successful");
        } catch (RuntimeException re) {
            log.error("persist candidat failed", re);
            throw re;
        }
    }

    public void remove(Utilisateur persistentInstance) {
        log.debug("removing Utilisateur instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public Utilisateur merge(Utilisateur detachedInstance) {
        log.debug("merging Utilisateur instance");
        try {
            Utilisateur result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public Utilisateur findById(int id) {
        log.debug("getting Utilisateur instance with id: " + id);
        try {
            Utilisateur instance = entityManager.find(Utilisateur.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
