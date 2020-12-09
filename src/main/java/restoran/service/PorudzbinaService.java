package restoran.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import restoran.model.Porudzbina;


public interface PorudzbinaService {
	
	Porudzbina getOne(Integer id);
	List<Porudzbina> findAll();
	Page<Porudzbina> findAll(int pageNum);
	Porudzbina save(Porudzbina porudzbina);
	Porudzbina delete(Integer id);
	
	List<Porudzbina> findByIdBlagajne(Integer blagajnaId);
	
	Page<Porudzbina> findByIdUsera(Integer userId,int pageNum);

	Page<Porudzbina> search(
			@Param("datum") String datum, 
			@Param("userId") Integer userId, 
			int pageNum);
	
	Page<Porudzbina> svePoDatumu(
			@Param("datum") String datum, 
			int pageNum);

	Double UkupnoZaPlacanje(Integer id);
	void Plati(Integer id);

}
