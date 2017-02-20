package be.vdab.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "artikelgroepen")
public class Artikelgroep implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@OneToMany(mappedBy = "artikelgroep")
//	@JoinColumn(name = "artikelgroepid")
	@OrderBy("naam")
	private Set<Artikel> artikels;

	protected Artikelgroep() {
	}

	public Artikelgroep(String naam) {
		setNaam(naam);
		artikels = new LinkedHashSet<>();
	}

	public long getId() {
		return id;
	}

	public final void setNaam(String naam) {
		if (!isValidNaam(naam)) {
			throw new IllegalArgumentException("Naam mag niet leeg zijn");
		}
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}

	public static boolean isValidNaam(String voornaam) {
		return voornaam != null && !voornaam.trim().isEmpty();
	}

	public Set<Artikel> getArtikels() {
		return Collections.unmodifiableSet(artikels);
	}

	public void add(Artikel artikel) {
		artikels.add(artikel);
		if (artikel.getArtikelgroep() != this) { 
			artikel.setArtikelgroep(this);
		}
	}

	public void remove(Artikel artikel) {
		artikels.remove(artikel);
		if (artikel.getArtikelgroep() == this) { 
			artikel.setArtikelgroep(null);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naam == null) ? 0 : naam.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Artikelgroep)) {
			return false;
		}
		Artikelgroep other = (Artikelgroep) obj;
		return naam.equalsIgnoreCase(other.naam);
	}

}
