package restoran.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty; 

@Entity
@Table(name="user")
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer id;
	
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a username")
	@Length(min = 5, message = "*Your user name must have at least 5 characters")
	private String username;
	
	@Column(nullable=false)
	@NotEmpty
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	private String password;

	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	List<Porudzbina> porudzbine = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
	}
	public void removeRole(Role role) {
		roles.remove(role);
		role.getUsers().remove(this);
    }
	
	
	
	
	
	public List<Porudzbina> getPorudzbine() {
		return porudzbine;
	}
	public void setPorudzbine(List<Porudzbina> porudzbine) {
		this.porudzbine = porudzbine;
	}
	public void addPorudzbina(Porudzbina porudzbina) {
		if(porudzbina.getUser() != this) {
			porudzbina.setUser(this);
		}
		porudzbine.add(porudzbina);
	}
	
	
	
	
}
