package restoran.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import restoran.model.Blagajna;
import restoran.repository.BlagajnaRepository;
import restoran.service.BlagajnaService;

@Service
public class JpaBlagajnaService implements BlagajnaService {
	
	@Autowired
	private BlagajnaRepository blagajnaRepository;

	@Override
	public Blagajna getOne(Integer id) {
		return blagajnaRepository.getOne(id);
	}

	@Override
	public List<Blagajna> findAll() {
		return blagajnaRepository.findAll();
	}

	@Override
	public Page<Blagajna> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return blagajnaRepository.findAll(pageable);
	}

	@Override
	public Blagajna save(Blagajna blagajna) {
		return blagajnaRepository.save(blagajna);
	}

	@Override
	public Blagajna delete(Integer id) {
		Blagajna blagajna = blagajnaRepository.getOne(id);
		if(blagajna != null) {
			blagajnaRepository.delete(blagajna);
		}
		return blagajna;
	}
	
	@Override
	public Page<Blagajna> search(String datum, Double ukupno, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return blagajnaRepository.search(datum, ukupno, pageable);
	}

	@Override
	public Blagajna findByDatum(String datum) {
		return blagajnaRepository.findByDatum(datum);
	}
	
	@Override
	public void otvoriBlagajnu() {
		Blagajna blagajna = new Blagajna();
		blagajnaRepository.save(blagajna);
	}

	@Override
	public void zatvoriBlagajnu() {
		List<Blagajna> blagajne = blagajnaRepository.findAll();
    	for(Blagajna blagajna : blagajne) {
    		blagajna.setClosed(true);
    		blagajnaRepository.save(blagajna);
    	}
	}





	



}
