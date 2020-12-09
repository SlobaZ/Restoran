package restoran.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import restoran.model.Jedinica;
import restoran.model.Jedinica.Kategorija;



@Controller
public class MeniController {

	 List<Jedinica> jediniceHrane = new ArrayList<>();
	 List<Jedinica> jedinicePica = new ArrayList<>();
	 List<Jedinica> jediniceSalata = new ArrayList<>();
	
	{
	 for (Jedinica v : restoran.model.Jedinica.values()) {
		  if(v.getKategorija()==Kategorija.Hrana) {
			 jediniceHrane.add(v);
		  }
		  if(v.getKategorija()==Kategorija.Pice) {
			  jedinicePica.add(v);
		  }
		  if(v.getKategorija()==Kategorija.Salata) {
			  jediniceSalata.add(v);
		  }
	  }	 
	}
	
	@RequestMapping(value ="/meni", method = RequestMethod.GET)
	public String getAll(@RequestParam (required = false) Kategorija kategorija,Model model) {
		
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
		return "meni";
	}

		

	
	
	

}
