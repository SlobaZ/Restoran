package restoran.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import restoran.pomocno.DatumIVreme;

@Entity
@Table(name="blagajna")
public class Blagajna implements DatumIVreme {
	
	@Id
	@GeneratedValue
	@Column
	private Integer id;
	
	@OneToMany(mappedBy="blagajna", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Porudzbina> porudzbine = new ArrayList<Porudzbina>();
	
	@Column
	private String datum;
	
	@Column
	private Double ukupno;
	
	@Column
	private boolean closed;

	public Blagajna() {
		this.datum = DatumIVreme.UpisiSadasnjiDatumString();
		this.ukupno = 0.0;
		this.closed = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Porudzbina> getPorudzbine() {
		return porudzbine;
	}

	public void setPorudzbine(List<Porudzbina> porudzbine) {
		this.porudzbine = porudzbine;
	}
	
	public void addPorudzbina(Porudzbina porudzbina) {
		if(porudzbina.getBlagajna() != this) {
			porudzbina.setBlagajna(this);
		}
		porudzbine.add(porudzbina);
	}
	
	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public Double getUkupno() {
		return ukupno;
	}

	public void setUkupno(Double ukupno) {
		this.ukupno = ukupno;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	
	

}
