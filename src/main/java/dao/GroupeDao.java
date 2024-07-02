package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Groupe;

public class GroupeDao {
    private SessionFactory sessionFactory;

    public GroupeDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Groupe> getAllGroupes() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Groupe", Groupe.class).list();
        }
    }

    public Groupe addGroupe(Groupe groupe) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(groupe);
            transaction.commit();
            return groupe;
        }
    }

    public void deleteGroupe(Groupe groupe) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(groupe);
            transaction.commit();
        }
    }

    public List<Groupe> getGroupByIdEnfant(Long enfantId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Groupe> query = session.createQuery("from Groupe where enfant_id = :enfantId", Groupe.class);
            query.setParameter("enfantId", enfantId);
            return query.list();
        }
    }

	public List<Groupe> getAllGroupe() {
		// TODO Auto-generated method stub
		return null;
	}

	public Groupe getGroupeById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Groupe updateGroupe(Groupe groupe) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Groupe> getGroupeByEnfantId(Long enfant_Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
