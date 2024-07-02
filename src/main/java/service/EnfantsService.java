package service;

import dao.EnfantsDao;
import model.Enfants;

import java.util.List;

public class EnfantsService {
    private EnfantsDao enfantsDao;

    public EnfantsService() {
        this.enfantsDao = new EnfantsDao(); // Initialize your DAO instance
    }

    public List<Enfants> getAllEnfants() {
        return enfantsDao.getAllEnfants();
    }

    public Enfants getEnfantById(Long id) {
        return enfantsDao.getEnfantById(id);
    }

    public Enfants addEnfant(Enfants enfant) {
        // You can add additional business logic here if needed
        return enfantsDao.addEnfant(enfant);
    }

    public Enfants updateEnfant(Enfants enfant) {
        // You can add additional business logic here if needed
        return enfantsDao.updateEnfant(enfant);
    }

    public void deleteEnfant(Enfants enfant) {
        enfantsDao.deleteEnfant(enfant);
    }
}
