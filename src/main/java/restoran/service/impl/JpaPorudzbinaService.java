package restoran.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import restoran.model.Blagajna;
import restoran.model.Jedinica;
import restoran.model.Porudzbina;
import restoran.repository.PorudzbinaRepository;
import restoran.service.BlagajnaService;
import restoran.service.PorudzbinaService;


@Service
public class JpaPorudzbinaService implements PorudzbinaService {
	
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	
	
	@Autowired
	private BlagajnaService blagajnaService;

	@Override
	public Porudzbina getOne(Integer id) {
		return porudzbinaRepository.getOne(id);
	}

	@Override
	public List<Porudzbina> findAll() {
		return porudzbinaRepository.findAll();
	}

	@Override
	public Page<Porudzbina> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return porudzbinaRepository.findAll(pageable);
	}

	@Override
	public Porudzbina save(Porudzbina porudzbina) {
		return porudzbinaRepository.save(porudzbina);
	}

	@Override
	public Porudzbina delete(Integer id) {
		Porudzbina porudzbina = porudzbinaRepository.getOne(id);
		if(porudzbina != null) {
			porudzbinaRepository.delete(porudzbina);
		}
		return porudzbina;
	}

	@Override
	public List<Porudzbina> findByIdBlagajne(Integer blagajnaId) {
		return porudzbinaRepository.findByIdBlagajne(blagajnaId);
	}
	
	@Override
	public Page<Porudzbina> findByIdUsera(Integer userId, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return porudzbinaRepository.findByIdUsera(userId, pageable);
	}
	
	@Override
	public Page<Porudzbina> search(String datum, Integer userId, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return porudzbinaRepository.search(datum, userId, pageable);
	}
	
	@Override
	public Page<Porudzbina> svePoDatumu(String datum, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return porudzbinaRepository.svePoDatumu(datum, pageable);
	}

	
	@Override
	public Double UkupnoZaPlacanje(Integer id) {
		Porudzbina porudzbina = porudzbinaRepository.getOne(id);
		Double UkupnoZaPlacanje = 0.0;
		for(Jedinica jedinica : porudzbina.getJedinice()) {
			UkupnoZaPlacanje += jedinica.getCena();
		}
		return UkupnoZaPlacanje;
	}

	@Override
	public void Plati(Integer id) {
		Porudzbina porudzbina = porudzbinaRepository.getOne(id);
		porudzbina.setPlaceno(true); 
	    Blagajna blagajna = blagajnaService.getOne(porudzbina.getBlagajna().getId());
		blagajna.setUkupno(blagajna.getUkupno() + porudzbina.getUkupnaCena());
		porudzbinaRepository.save(porudzbina);
		blagajnaService.save(blagajna);
	}













	
	

}
