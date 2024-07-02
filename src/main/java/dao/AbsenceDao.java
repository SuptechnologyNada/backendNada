package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Absence;

public class AbsenceDao {
    private SessionFactory sessionFactory;

    public AbsenceDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Absence> getAllAbsences() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Absence", Absence.class).list();
        }
    }

    public Absence addAbsence(Absence absence) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(absence);
            transaction.commit();
            return absence;
        }
    }

    public void deleteAbsence(Absence absence) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(absence);
            transaction.commit();
        }
    }

    public List<Absence> getAbsencesByEnfantId(Long enfant_Id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Absence> query = session.createQuery("from Absence where enfant_Id = :enfantId", Absence.class);
            query.setParameter("enfantId", enfant_Id);
            return query.list();
        }
    }

}
