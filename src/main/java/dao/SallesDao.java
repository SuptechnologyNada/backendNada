package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Salles;

public class SallesDao {
    private SessionFactory sessionFactory;

    public SallesDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Salles> getAllSalles() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Salles", Salles.class).list();
        }
    }

    public Salles getSalleById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Salles.class, id);
        }
    }

    public Salles addSalle(Salles salle) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(salle);
            transaction.commit();
            return salle;
        }
    }

    public Salles updateSalle(Salles salle) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(salle);
            transaction.commit();
            return salle;
        }
    }

    public void deleteSalle(Salles salle) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(salle);
            transaction.commit();
        }
    }

    public List<Salles> getSallesByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Salles> query = session.createQuery("select from Salles where enfant_id = :enfantId", Salles.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        }
    }
}
