
package model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seances")
public class Seance {
    @Id
    private Long id;
    private String heure;
    private LocalDate date;
    private String enfant_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getEnfant_id() {
		return enfant_id;
	}
	public void setEnfant_id(String enfant_id) {
		this.enfant_id = enfant_id;
	}
	public Object getNom() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
