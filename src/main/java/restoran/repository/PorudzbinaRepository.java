package restoran.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import restoran.model.Porudzbina;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Integer> {
	
	@Query("SELECT p FROM Porudzbina p WHERE p.blagajna.id = :blagajnaId ")
	List<Porudzbina> findByIdBlagajne(Integer blagajnaId);
	
	@Query("SELECT p FROM Porudzbina p WHERE p.user.id = :userId ")
	Page<Porudzbina> findByIdUsera(Integer userId,Pageable pageRequest);

	@Query("SELECT p FROM Porudzbina p WHERE "
			+ "(:datum IS NULL or p.blagajna.datum = :datum ) AND "
			+ "( p.user.id = :userId ) "
			)
	Page<Porudzbina> search(
			@Param("datum") String datum, 
			Integer userId, 
			Pageable pageRequest);
	
	
	
	@Query("SELECT p FROM Porudzbina p WHERE :datum IS NULL or p.blagajna.datum = :datum ")
	Page<Porudzbina> svePoDatumu(
			@Param("datum") String datum, 
			Pageable pageRequest);
	
	

}
