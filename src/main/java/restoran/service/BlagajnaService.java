package restoran.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import restoran.model.Blagajna;


public interface BlagajnaService {
	
	Blagajna getOne(Integer id);
	List<Blagajna> findAll();
	Page<Blagajna> findAll(int pageNum);
	Blagajna save(Blagajna blagajna);
	Blagajna delete(Integer id);
	
	Page<Blagajna> search(
			@Param("datum") String datum, 
			@Param("ukupno") Double ukupno, 
			 int pageNum);
	
	Blagajna findByDatum(String datum);	
	
	void otvoriBlagajnu();
	void zatvoriBlagajnu();

}
