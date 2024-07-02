package service;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import dao.LoginDao;
import model.Login;

public class LoginService {
	SessionFactory sessionFactory;
	private LoginDao loginDao;
    

    public LoginService() {
        this.loginDao = new LoginDao();
        this.sessionFactory = HibernateUtil.getSessionFactory();

    }

    public List<Login> getAllLogins() {
        return loginDao.getAllLogins();
    }

    public Login addLogin(Login login) {
        return loginDao.addLogin(login);
    }

    public void deleteLogin(Login login) {
        loginDao.deleteLogin(login);
    }
    
    public Login authenticate(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Login> query = session.createQuery("FROM Login WHERE username = :username AND password = :password", Login.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            Login login = query.uniqueResult();
            if (login != null) {
                System.out.println("user authenticated: " + login.getUsername());
                return login;
            } else {
                System.out.println("user authentication failed for email: " + username);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}