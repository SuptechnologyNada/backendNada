package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import model.Enfants;

public class EnfantsDao {
    private SessionFactory sessionFactory;

    public EnfantsDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Enfants> getAllEnfants() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Enfants", Enfants.class).list();
        }
    }

    public Enfants getEnfantById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Enfants.class, id);
        }
    }

    public Enfants addEnfant(Enfants enfant) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(enfant);
            transaction.commit();
            return enfant;
        }
    }

    public Enfants updateEnfant(Enfants enfant) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(enfant);
            transaction.commit();
            return enfant;
        }
    }

    public void deleteEnfant(Enfants enfant) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(enfant);
            transaction.commit();
        }
    }
}
