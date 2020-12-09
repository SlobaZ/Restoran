package restoran;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import restoran.model.Role;
import restoran.model.User;
import restoran.model.Blagajna;
import restoran.model.Jedinica;
import restoran.model.Porudzbina;
import restoran.repository.RoleRepository;
import restoran.security.UserService;
import restoran.service.BlagajnaService;
import restoran.service.PorudzbinaService;


@Component
public class TestData {
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BlagajnaService blagajnaService;

		
	@Autowired
	private PorudzbinaService porudzbinaService;
	
	
	@PostConstruct
	public void init() {
		
		Role role1 = new Role();
		role1.setName("ROLE_ADMIN");
		role1 = roleRepository.save(role1);
		
		Role role2 = new Role();
		role2.setName("ROLE_KONOBAR");
		role2 = roleRepository.save(role2);
		
		User user1 = new User();
		user1.setUsername("Admin");
		user1.setPassword("$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS");
		user1 = userService.save(user1);

		User user2 = new User();
		user2.setUsername("Konobar1");
		user2.setPassword("$2a$10$6K1uK3otA5wBppQ6X3hd9.XrN2FCr24Vp6EERaq4yAyXrkfjOi50q");
		user2 = userService.save(user2);
		
		User user3 = new User();
		user3.setUsername("Konobar2");
		user3.setPassword("$2a$10$PhSSh18QwuEZs6mbdI7R2eRg5HBb1CP/ZjM2h0e/10.SpisuN2q72");
		user3 = userService.save(user3);
		
	
		user1.addRole(role1);
		user2.addRole(role2);
		user3.addRole(role2);
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);

	
		Blagajna blagajna1 = new Blagajna();
		blagajna1.setDatum("03.12.2020.");
		blagajna1 = blagajnaService.save(blagajna1);
					
		
		Porudzbina porudzbina1 = new Porudzbina();
		porudzbina1.setDatumVreme("03.12.2020. 15:45");
		porudzbina1.setUser(user2);
		porudzbina1.setBlagajna(blagajna1);
		porudzbina1 = porudzbinaService.save(porudzbina1);
		
		porudzbina1.addJedinica(Jedinica.PASULJ);
		porudzbina1.addJedinica(Jedinica.PAPRIKAS);
		porudzbina1.addJedinica(Jedinica.PASULJ);
		porudzbina1.addJedinica(Jedinica.PIVO);
		porudzbina1.addJedinica(Jedinica.VINO);
		porudzbina1.setUkupnaCena();
		porudzbina1.setPlaceno(true);
		porudzbina1 = porudzbinaService.save(porudzbina1);
		
	
		
		Porudzbina porudzbina2 = new Porudzbina();
		porudzbina2.setDatumVreme("03.12.2020. 11:30");
		porudzbina2.setUser(user3);
		porudzbina2.setBlagajna(blagajna1);
		porudzbina2 = porudzbinaService.save(porudzbina2);
		
		porudzbina2.addJedinica(Jedinica.BECKASNICLA);
		porudzbina2.addJedinica(Jedinica.PILETINA);
		porudzbina2.setUkupnaCena();
		porudzbina2.setPlaceno(true);
		porudzbina2 = porudzbinaService.save(porudzbina2);
		
		blagajna1.setUkupno(porudzbina1.getUkupnaCena()+porudzbina2.getUkupnaCena());
		blagajna1.setClosed(true);		
		blagajnaService.save(blagajna1);
	
	}
	
			//sekund minut sat dan mesec daniunedelji
	@Scheduled(cron = "0 30 17 * * 0-6")
	public void doOtvoriBlagajnu() {
		System.out.println("Cron Otvori !!!!!!!!!");
		blagajnaService.otvoriBlagajnu();

	}
	
					//sekund minut sat dan mesec daniunedelji
	@Scheduled(cron = "0 50 23 * * 0-6")
    public void doZatvoriBlagajnu() {
		System.out.println("Cron Zatvori !!!!!!!!!");
		 blagajnaService.zatvoriBlagajnu();
 
    }

}
