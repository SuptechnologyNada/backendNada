package service;

import dao.SallesDao;
import model.Salles;

import java.util.List;

public class SallesService {
    private SallesDao sallesDao;

    public SallesService() {
        this.sallesDao = new SallesDao(); // Initialize your DAO instance
    }

    public List<Salles> getAllSalles() {
        return sallesDao.getAllSalles();
    }

    public Salles getSalleById(Long id) {
        return sallesDao.getSalleById(id);
    }

    public Salles addSalle(Salles salle) {
        // You can add additional business logic here if needed
        return sallesDao.addSalle(salle);
    }

    public Salles updateSalle(Salles salle) {
        // You can add additional business logic here if needed
        return sallesDao.updateSalle(salle);
    }

    public void deleteSalle(Salles salle) {
        sallesDao.deleteSalle(salle);
    }

    public List<Salles> getSallesByEnfantId(Long enfant_Id) {
        return sallesDao.getSallesByEnfantId(enfant_Id);
    }
}
