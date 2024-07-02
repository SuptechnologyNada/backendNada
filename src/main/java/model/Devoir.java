
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devoirs")
public class Devoir{
    @Id
    private Long id;
    private String nom;
    private String description;
    private Long enfant_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getEnfants_id() {
		return enfant_id;
	}
	public void setEnfants_id(Long enfants_id) {
		this.enfant_id = enfants_id;
	}

   
}
