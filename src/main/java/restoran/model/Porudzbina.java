package restoran.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import restoran.pomocno.DatumIVreme;

@Entity
@Table(name="porudzbina")
public class Porudzbina implements DatumIVreme {
	
	@Id
	@GeneratedValue
	@Column
	private Integer id;
	
	  @ElementCollection(targetClass = Jedinica.class)
	  @CollectionTable(name = "porudzbina_jedinica",
	            joinColumns = @JoinColumn(name = "porudzbina_id"))
	  @Enumerated(EnumType.STRING)
	  @Column(name = "jedinica_naziv")
	  private List<Jedinica> jedinice = new ArrayList<Jedinica>();

	
	@Column
	private String datumVreme;
	@Column
	private Double ukupnaCena;
	@Column
	private boolean placeno;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "blagajna")
	private Blagajna blagajna;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user")
	private User user;
	
	public Porudzbina() {
		this.datumVreme = DatumIVreme.UpisiSadasnjiDatumIVremeString();
		this.placeno = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public List<Jedinica> getJedinice() {
		return jedinice;
	}

	public void setJedinice(List<Jedinica> jedinice) {
		this.jedinice = jedinice;
	}

	public void addJedinica(Jedinica jedinica) {
		jedinice.add(jedinica);
		jedinica.getPorudzbine().add(this);

	}
	
	public String getDatumVreme() {
		return datumVreme;
	}

	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}

	public Double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena() {
		this.ukupnaCena  = UkupnoZaPlacanje();
	}

	public boolean isPlaceno() {
		return placeno;
	}

	public void setPlaceno(boolean placeno) {
		this.placeno = placeno;
	}
	
	public Double UkupnoZaPlacanje(){
		Double UkupnoZaPlacanje = 0.0;
		for(Jedinica jedinica : jedinice) {
			UkupnoZaPlacanje += jedinica.getCena();
		}
		return UkupnoZaPlacanje;
	}
	
	public boolean Plati() {
		placeno = true;
		blagajna.setUkupno(blagajna.getUkupno() + ukupnaCena);
		return placeno;
	}

	public Blagajna getBlagajna() {
		return blagajna;
	}

	public void setBlagajna(Blagajna blagajna) {
		this.blagajna = blagajna;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
	
	
	

}
