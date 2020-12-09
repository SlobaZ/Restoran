package restoran.model;

import java.util.ArrayList;
import java.util.List;

public enum Jedinica {
	
	TELECACORBA("Teleca corba",250.0,Kategorija.Hrana),
	PARADAJZCORBA("Paradajz corba",200.0,Kategorija.Hrana),
	PASULJ("Pasulj",500.0,Kategorija.Hrana),
	PAPRIKAS("Paprikas",700.0,Kategorija.Hrana),
	BECKASNICLA("Becka Snicla",800.0,Kategorija.Hrana),
	PUNJENEPAPRIKE("Punjene paprike",600.0,Kategorija.Hrana),
	PILETINA("Piletina",700.0,Kategorija.Hrana),
	COCACOLA("Coca Cola",150.0,Kategorija.Pice),
	PIVO("Pivo", 200.0,Kategorija.Pice),
	VINO("Vino",250.0,Kategorija.Pice),
	PARADAJZ("Paradajz",200.0,Kategorija.Salata),
	KUPUS("Kupus",100.0,Kategorija.Salata),
	SOPSKA("Sopska",200.0,Kategorija.Salata);
	
	public enum Kategorija{
		Hrana("Hrana"), Pice("Pice"), Salata("Salata");
		private String naziv;

		 Kategorija(String naziv) {
			this.naziv = naziv;
		}

		public String getNaziv() {
			return naziv;
		}

		public void setNaziv(String naziv) {
			this.naziv = naziv;
		}
		
	}

	private String naziv;
	private Double cena;
	private Kategorija kategorija;
	

	private List<Porudzbina> porudzbine = new ArrayList<>();
	
	

	private Jedinica(String naziv, Double cena, Kategorija kategorija) {
		this.naziv = naziv;
		this.cena = cena;
		this.kategorija = kategorija;
	}


	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}


	public List<Porudzbina> getPorudzbine() {
		return porudzbine;
	}
	public void setPorudzbine(List<Porudzbina> porudzbine) {
		this.porudzbine = porudzbine;
	}
	public void addPorudzbina(Porudzbina porudzbina) {
		porudzbine.add(porudzbina);
		porudzbina.getJedinice().add(this);
	}
	
	
	

}


