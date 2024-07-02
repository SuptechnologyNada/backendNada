package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Absence;
import model.Classe;

public class ClasseDao {
	private SessionFactory sessionFactory;

    public ClasseDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Classe> getAllClasses() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Classes", Classe.class).list();
        }
    }

    public Classe addClasse(Classe classe) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(classe);
            transaction.commit();
            return classe;
        }
    }

    public void deleteClasse(Classe classe) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(classe);
            transaction.commit();
        }
    }
    public List<Classe> getClasseByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Classe> query = session.createQuery("from Classe where enfant_Id = :enfantId", Classe.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        }
    }

    

}
