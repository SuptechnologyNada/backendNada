package service;

import dao.ClasseDao;

import model.Classe;

import java.util.List;

public class ClasseService {
    private ClasseDao classeDao;

    public ClasseService() {
        this.classeDao = new ClasseDao(); // Initialize your DAO instance
    }

    public List<Classe> getAllClasses() {
        return classeDao.getAllClasses();
    }

    public Classe addClasse(Classe classe) {
        // You can add additional business logic here if needed
        return classeDao.addClasse(classe);
    }

    public void deleteClasse(Classe classe) {
        classeDao.deleteClasse(classe);
    }
    public List<Classe> getAbsencesByEnfantId(Long enfant_Id) {
        return classeDao.getClasseByEnfantId(enfant_Id);
    }
}
