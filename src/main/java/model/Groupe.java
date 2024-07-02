
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groupes")
public class Groupe {
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
	public Long getEnfant_id() {
		return enfant_id;
	}
	public void setEnfant_id(Long enfant_id) {
		this.enfant_id = enfant_id;
	}

   
}
