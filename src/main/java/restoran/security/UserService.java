package restoran.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import restoran.model.Role;
import restoran.model.User;
import restoran.repository.RoleRepository;
import restoran.repository.UserRepository;



@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
	
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_KONOBAR");
        user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    
	public User getOne(Integer id) {
		return userRepository.getOne(id);
	};
	
	public List<User> findAll(){
		return userRepository.findAll();
	};
	
	public Page<User> findAll(int pageNum){
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return userRepository.findAll(pageable);
	};
	
	public User delete(Integer id) {
		User user = userRepository.getOne(id);
		if(user!=null) {
			userRepository.delete(user);
		}
		return user;
	};
	
	public Page<User> search( @Param("username") String username,  int pageNum){
		if( username != null) {
			username = '%' + username + '%';
		}

		PageRequest pageable = PageRequest.of(pageNum, 5);
		return userRepository.search(username, pageable);
	};
    
    
    
    
    
    
	
}
