package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Login;

public class LoginDao {
    private SessionFactory sessionFactory;

    public LoginDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Login> getAllLogins() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Login", Login.class).list();
        }
    }

    public Login addLogin(Login login) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(login);
            transaction.commit();
            return login;
        }
    }

    public void deleteLogin(Login login) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(login);
            transaction.commit();
        }
    }
}