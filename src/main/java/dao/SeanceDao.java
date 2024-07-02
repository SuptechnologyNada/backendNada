package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import model.Seance;

public class SeanceDao {
    private SessionFactory sessionFactory;

    public SeanceDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Seance> getAllSeances() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Seance", Seance.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Seance getSeanceById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Seance.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Seance addSeance(Seance seance) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(seance);
            transaction.commit();
            return seance;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public Seance updateSeance(Seance seance) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(seance);
            transaction.commit();
            return seance;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public void deleteSeance(Seance seance) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(seance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Seance> getSeancesByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Seance> query = session.createQuery("from Seance where enfant_id = :enfantId", Seance.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
