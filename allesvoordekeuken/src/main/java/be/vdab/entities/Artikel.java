package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import be.vdab.valueobjects.Korting;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "artikels")
@DiscriminatorColumn(name = "soort")
public abstract class Artikel implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final BigDecimal HONDERD = BigDecimal.valueOf(100);
	private static final BigDecimal MIN_AANKOOPPRIJS = BigDecimal.valueOf(0.01);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;
	@ElementCollection
	@CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelid"))
	@OrderBy("vanafAantal")
	private Set<Korting> kortingen;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artikelgroepid")
	private Artikelgroep artikelgroep;


	protected Artikel() {
	}

	public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, Artikelgroep artikelgroep) {
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setVerkoopprijs(verkoopprijs);
		setArtikelgroep(artikelgroep);
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

	public final void setAankoopprijs(BigDecimal aankoopprijs) {
		if (!isValidAankoopprijs(aankoopprijs)) {
			throw new IllegalArgumentException("Aankoopprijs moet minstens 0.01 zijn");
		}
		this.aankoopprijs = aankoopprijs;
	}

	public BigDecimal getAankoopprijs() {
		return aankoopprijs;
	}

	public final void setVerkoopprijs(BigDecimal verkoopprijs) {
		if (!isValidVerkoopprijs(verkoopprijs, aankoopprijs)) {
			throw new IllegalArgumentException("Verkoopprijs mag niet kleiner zijn dan aankoopprijs");
		}
		this.verkoopprijs = verkoopprijs;
	}

	public BigDecimal getVerkoopprijs() {
		return verkoopprijs;
	}
	
	public Set<Korting> getKortingen() {
		return Collections.unmodifiableSet(kortingen);
	}
	
	public static boolean isValidNaam(String voornaam) {
		return voornaam != null && !voornaam.trim().isEmpty();
	}

	public static boolean isValidAankoopprijs(BigDecimal aankoopprijs) {
		return aankoopprijs != null && aankoopprijs.compareTo(MIN_AANKOOPPRIJS) >= 0;
	}

	public static boolean isValidVerkoopprijs(BigDecimal verkoopprijs, BigDecimal aankoopprijs) {
		return verkoopprijs != null && verkoopprijs.compareTo(aankoopprijs) >= 0;
	}

	public BigDecimal getWinst() {
		if (aankoopprijs != null && aankoopprijs != BigDecimal.ZERO) {
			return verkoopprijs.subtract(aankoopprijs).divide(aankoopprijs, 4, RoundingMode.HALF_UP).multiply(HONDERD);
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	public Artikelgroep getArtikelgroep() {
		return artikelgroep;
	}
	public void setArtikelgroep(Artikelgroep artikelgroep) {
		if (this.artikelgroep != null && this.artikelgroep.getArtikels().contains(this)) {
			this.artikelgroep.remove(this);
		}
		this.artikelgroep = artikelgroep;
		if (artikelgroep != null && ! artikelgroep.getArtikels().contains(this)) {
			artikelgroep.add(this);
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
		if (!(obj instanceof Artikel)) {
			return false;
		}
		Artikel other = (Artikel) obj;
		return naam.equalsIgnoreCase(other.naam);
	}


}
