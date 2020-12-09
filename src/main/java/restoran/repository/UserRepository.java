package restoran.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import restoran.model.Porudzbina;
import restoran.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE  u.username = :username ")
	User findByUserName(String username);
	
	
	@Query("SELECT u FROM User u WHERE :username IS NULL or u.username like :username ")
	Page<User> search(@Param("username") String username, Pageable pageRequest);
			
	
	@Query("SELECT p FROM Porudzbina p WHERE p.user.id = :idK")
	Porudzbina podatakKorisnika( @Param("idK") Integer idK);
	
	

}
