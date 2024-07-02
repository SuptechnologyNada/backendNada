package service;

import dao.RecommandationsDao;
import model.Recommandations;

import java.util.List;

public class RecommandationsService {
    private RecommandationsDao recommandationsDao;

    public RecommandationsService() {
        this.recommandationsDao = new RecommandationsDao(); // Initialize your DAO instance
    }

    public List<Recommandations> getAllRecommandations() {
        return recommandationsDao.getAllRecommandations();
    }

    public Recommandations getRecommandationById(Long id) {
        return recommandationsDao.getRecommandationById(id);
    }

    public Recommandations addRecommandation(Recommandations recommandation) {
        // You can add additional business logic here if needed
        return recommandationsDao.addRecommandation(recommandation);
    }

    public Recommandations updateRecommandation(Recommandations recommandation) {
        // You can add additional business logic here if needed
        return recommandationsDao.updateRecommandation(recommandation);
    }

    public void deleteRecommandation(Recommandations recommandation) {
        recommandationsDao.deleteRecommandation(recommandation);
    }

    public List<Recommandations> getRecommandationsByEnfantId(Long enfant_Id) {
        return recommandationsDao.getRecommandationsByEnfantId(enfant_Id);
    }
}
