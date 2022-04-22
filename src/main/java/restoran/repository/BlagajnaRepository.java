package restoran.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import restoran.model.Blagajna;


@Repository
public interface BlagajnaRepository extends JpaRepository<Blagajna, Integer> {
	
	@Query("SELECT b FROM Blagajna b WHERE "
			+ "(:datum IS NULL or b.datum like :datum ) AND "
			+ "(:ukupno IS NULL or b.ukupno = :ukupno ) "
			)
	Page<Blagajna> search(
			@Param("datum") String datum, 
			@Param("ukupno") Double ukupno, 
			Pageable pageRequest);
	
	Blagajna findByDatum(String datum);


}
