package service;

import dao.GroupeDao;
import model.Groupe;

import java.util.List;

public class GroupeService {
    private GroupeDao groupeDao;

    public GroupeService() {
        this.groupeDao = new GroupeDao(); // Initialize your DAO instance
    }

    public List<Groupe> getAllGroupe() {
        return groupeDao.getAllGroupe();
    }

    public Groupe getGroupeById(Long id) {
        return groupeDao.getGroupeById(id);
    }

    public Groupe addGroupe(Groupe groupe) {
        // You can add additional business logic here if needed
        return groupeDao.addGroupe(groupe);
    }

    public Groupe updateGroupe(Groupe groupe) {
        // You can add additional business logic here if needed
        return groupeDao.updateGroupe(groupe);
    }

    public void deleteGroupe(Groupe groupe) {
    	groupeDao.deleteGroupe(groupe);
    }

    public List<Groupe> getGroupeByEnfantId(Long enfant_Id) {
        return groupeDao.getGroupeByEnfantId(enfant_Id);
    }
}

