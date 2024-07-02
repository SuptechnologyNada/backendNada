package service;

import dao.SeanceDao;
import model.Seance;

import java.util.List;

public class SeanceService {
    private SeanceDao seanceDao;

    public SeanceService() {
        this.seanceDao = new SeanceDao(); // Initialize your DAO instance
    }

    public List<Seance> getAllSeances() {
        return seanceDao.getAllSeances();
    }

    public Seance getSeanceById(Long id) {
        return seanceDao.getSeanceById(id);
    }

    public Seance addSeance(Seance seance) {
        // You can add additional business logic here if needed
        return seanceDao.addSeance(seance);
    }

    public Seance updateSeance(Seance seance) {
        // You can add additional business logic here if needed
        return seanceDao.updateSeance(seance);
    }

    public void deleteSeance(Seance seance) {
        seanceDao.deleteSeance(seance);
    }

    public List<Seance> getSeancesByEnfantId(Long enfant_Id) {
        return seanceDao.getSeancesByEnfantId(enfant_Id);
    }
}
