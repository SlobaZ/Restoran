package restoran.web.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import restoran.model.Blagajna;
import restoran.model.Jedinica;
import restoran.model.Porudzbina;
import restoran.model.Role;
import restoran.model.User;
import restoran.pomocno.DatumIVreme;
import restoran.repository.PorudzbinaRepository;
import restoran.repository.RoleRepository;
import restoran.model.Jedinica.Kategorija;
import restoran.security.UserService;
import restoran.service.BlagajnaService;
import restoran.service.PorudzbinaService;



@Controller
public class PorudzbineController implements DatumIVreme {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BlagajnaService blagajnaService;
	
	@Autowired
	private PorudzbinaService porudzbinaService;
	
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	
	
	 List<Jedinica> jediniceHrane = new ArrayList<Jedinica>();
	 List<Jedinica> jedinicePica = new ArrayList<Jedinica>();
	 List<Jedinica> jediniceSalata = new ArrayList<Jedinica>();
	 
	 List<Jedinica> poruceneJedinice = new ArrayList<Jedinica>();
	 List<Jedinica> poruceneJediniceRezultat = new ArrayList<Jedinica>();
	
	{
	 for (Jedinica jedinica : restoran.model.Jedinica.values()) {
		  if(jedinica.getKategorija()==Kategorija.Hrana) {
			 jediniceHrane.add(jedinica);
		  }
		  if(jedinica.getKategorija()==Kategorija.Pice) {
			  jedinicePica.add(jedinica);
		  }
		  if(jedinica.getKategorija()==Kategorija.Salata) {
			  jediniceSalata.add(jedinica);
		  }
	  }	 
	}
	
	
	
	@RequestMapping(value ="/user/jedinice", method = RequestMethod.GET)
	public String kreirajPorudzbinu( @RequestParam (required = false) Kategorija kategorija,
									Model model,Principal principal) {

		poruceneJediniceRezultat.clear();
		
		User user =  userService.findByUserName(principal.getName());
	    model.addAttribute("pozdravnaPorukaUseru", "Welcome " + user.getUsername() );
	   	    
	    model.addAttribute("kategorije", restoran.model.Jedinica.Kategorija.values());
		
 		if(kategorija==restoran.model.Jedinica.Kategorija.valueOf("Hrana")) {
			model.addAttribute("jedinice", jediniceHrane);
		}
		else if(kategorija==restoran.model.Jedinica.Kategorija.valueOf("Pice")) {
			model.addAttribute("jedinice", jedinicePica);
		}
		else if(kategorija==restoran.model.Jedinica.Kategorija.valueOf("Salata")) {
			model.addAttribute("jedinice", jediniceSalata);
		}
		else {
		model.addAttribute("jedinice", restoran.model.Jedinica.values());
		}
		return "/konobar/jedinice";
	}


	
	@RequestMapping(value = "/user/porucijedinicu/{jedinica}", method = RequestMethod.GET)
	public String poruciJedinicu(@PathVariable("jedinica") Jedinica jedinica,
								  HttpServletRequest request, Model model) {
		
		poruceneJedinice.add(jedinica);
		model.addAttribute("poruceneJedinice", poruceneJedinice);
		
		  return "redirect:/user/jedinice";
	}
	
	
	  @GetMapping("/user/resetujjedinicu/{jedinica}")
	    private String resetujJedinicu(@PathVariable("jedinica") Jedinica jedinica, Model model){
		  
		  poruceneJedinice.remove(jedinica);
		  model.addAttribute("poruceneJedinice", poruceneJedinice);
		  
	        return "redirect:/user/jedinice";
	    }

	  @GetMapping("/user/poruci")
	    private String poruci( Model model,Principal principal){
	
		  User user =  userService.findByUserName(principal.getName());
		  model.addAttribute("pozdravnaPorukaUseru",  user.getUsername());
		    
		  Blagajna blagajna = blagajnaService.findByDatum(DatumIVreme.UpisiSadasnjiDatumString());

		  Porudzbina porudzbina = new Porudzbina();
		  porudzbina.setUser(user);
		  porudzbina.setBlagajna(blagajna);
		  porudzbinaService.save(porudzbina);
		  for(Jedinica jedinica: poruceneJedinice ) {
			  porudzbina.addJedinica(jedinica);
		  }
		  porudzbina.setUkupnaCena();
		  porudzbinaService.save(porudzbina);
		  
		  poruceneJediniceRezultat.addAll(poruceneJedinice);
		  model.addAttribute("poruceneJediniceRezultat", poruceneJediniceRezultat );
		  
		  model.addAttribute("porudzbina", porudzbina);
		  
		  poruceneJedinice.clear();
		  
		 return "/konobar/rezultatporudzbine";
	    }
	  
	  
	  
	  
	  @RequestMapping(value ="/user/porudzbinekonobara", method = RequestMethod.GET)
		public String porudzbineKonobara( @RequestParam (required = false) String datum, Model model,
											HttpServletRequest request,Principal principal ) {
		  
		  User user =  userService.findByUserName(principal.getName());
		  model.addAttribute("pozdravnaPorukaUseru",  user.getUsername());
		  
		  Role role = roleRepository.findByName("ROLE_ADMIN");
	
		  Integer userId = user.getId();
		  int page = 0;
		  int size = 5;
		  		    
		    Page<Porudzbina> porudzbinaPage = null;
		
				if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
					page = Integer.parseInt(request.getParameter("page")) - 1;
				}

				if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
					size = Integer.parseInt(request.getParameter("size"));
				}

			    if (datum != null) {
			    	if(user.getRoles().contains(role)) {
			    		porudzbinaPage = porudzbinaService.svePoDatumu(datum, page);
			    	}
			    	else {
			    		porudzbinaPage = porudzbinaService.search(datum, userId, page);
			    	}
			    }
			    
			    else {
			    	if(user.getRoles().contains(role)) {
			    		porudzbinaPage = porudzbinaService.findAll(page);
			    	}
			    	else {
			    		porudzbinaPage = porudzbinaRepository.findByIdUsera(userId, PageRequest.of(page, size));
			    	}
			    }

		  model.addAttribute("porudzbine", porudzbinaPage);

			 return "/konobar/porudzbinekonobara";
	    }
	  
	  
	  
	  	@GetMapping("/user/platiporudzbinu/{id}")
	    private String platiPorudzbinu(@PathVariable("id") Integer id, Model model){
		  
		  Porudzbina porudzbina = porudzbinaService.getOne(id);
		  porudzbina.setPlaceno(true);
		  porudzbinaService.save(porudzbina);
		  
		  Blagajna blagajna = porudzbina.getBlagajna();
		  blagajna.setUkupno(blagajna.getUkupno() + porudzbina.getUkupnaCena());
		  blagajnaService.save(blagajna);
		  		  
	        return "redirect:/user/porudzbinekonobara";
	    }
	  
	  
    
}
