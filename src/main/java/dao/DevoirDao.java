package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Devoir;

public class DevoirDao {
    private SessionFactory sessionFactory;

    public DevoirDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Devoir> getAllDevoirs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Devoir", Devoir.class).list();
        }
    }

    public Devoir addDevoir(Devoir devoir) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(devoir);
            transaction.commit();
            return devoir;
        }
    }

    public void deleteDevoir(Devoir devoir) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(devoir);
            transaction.commit();
        }
    }

    public List<Devoir> getDevoirsByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Devoir> query = session.createQuery("from Devoir where enfant_Id = :enfantId", Devoir.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        }
    }

}
