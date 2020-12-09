package restoran.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="roles")
public class Role {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "role_id")
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;
		
	@ManyToMany(mappedBy="roles")
	private List<User> users = new ArrayList<>();


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		users.add(user);
		user.getRoles().add(this);
	}
	public void removeUser(User user) {
		users.remove(user);
		user.getRoles().remove(this);
    }
	
	
	
	
	
	
}
