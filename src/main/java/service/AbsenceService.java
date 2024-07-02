package service;

import dao.AbsenceDao;
import model.Absence;

import java.util.List;

public class AbsenceService {
    private AbsenceDao absenceDao;

    public AbsenceService() {
        this.absenceDao = new AbsenceDao(); // Initialize your DAO instance
    }

    public List<Absence> getAllAbsences() {
        return absenceDao.getAllAbsences();
    }

    public Absence addAbsence(Absence absence) {
        // You can add additional business logic here if needed
        return absenceDao.addAbsence(absence);
    }

    public void deleteAbsence(Absence absence) {
        absenceDao.deleteAbsence(absence);
    }

    public List<Absence> getAbsencesByEnfantId(Long enfant_Id) {
        return absenceDao.getAbsencesByEnfantId(enfant_Id);
    }
}
