package model;


	import jakarta.persistence.Entity;

	import jakarta.persistence.Id;
	import jakarta.persistence.Table;

	
	@Entity
	@Table(name = "login") 




	
	public class Login {
			
			@Id
			String username;
			String password;
			int enfants_id;
			
			public int getEnfants_id() {
				return enfants_id;
			}
			public void setEnfants_id(int enfants_id) {
				this.enfants_id = enfants_id;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			
			
		}

