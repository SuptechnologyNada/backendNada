package service;

import dao.DevoirDao;
import model.Devoir;

import java.util.List;

public class DevoirService {
    private DevoirDao devoirDao;

    public DevoirService() {
        this.devoirDao = new DevoirDao(); // Initialize your DAO instance
    }

    public List<Devoir> getAllDevoirs() {
        return devoirDao.getAllDevoirs();
    }

    public Devoir addDevoir(Devoir devoir) {
        // You can add additional business logic here if needed
        return devoirDao.addDevoir(devoir);
    }

    public void deleteDevoir(Devoir devoir) {
        devoirDao.deleteDevoir(devoir);
    }

    public List<Devoir> getDevoirsByEnfantId(Long enfant_Id) {
        return devoirDao.getDevoirsByEnfantId(enfant_Id);
    }
}
