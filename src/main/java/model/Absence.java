package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class Absence {
    @Id
     Long id;
     LocalDate date;
     String raison;
     Long enfant_Id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getRaison() {
		return raison;
	}
	public void setRaison(String raison) {
		this.raison = raison;
	}
	public Long getEnfant_Id() {
		return enfant_Id;
	}
	public void setEnfant_Id(Long enfant_Id) {
		this.enfant_Id = enfant_Id;
	}
    
    
}
