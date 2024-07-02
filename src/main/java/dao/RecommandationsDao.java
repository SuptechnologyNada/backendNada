package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Recommandations;

public class RecommandationsDao {
    private SessionFactory sessionFactory;

    public RecommandationsDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Recommandations> getAllRecommandations() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Recommandations", Recommandations.class).list();
        }
    }

    public Recommandations getRecommandationById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Recommandations.class, id);
        }
    }

    public Recommandations addRecommandation(Recommandations recommandation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(recommandation);
            transaction.commit();
            return recommandation;
        }
    }

    public Recommandations updateRecommandation(Recommandations recommandation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(recommandation);
            transaction.commit();
            return recommandation;
        }
    }

    public void deleteRecommandation(Recommandations recommandation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(recommandation);
            transaction.commit();
        }
    }

    public List<Recommandations> getRecommandationsByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Recommandations> query = session.createQuery("from Recommandations where enfant_id = :enfantId", Recommandations.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        }
    }
}
