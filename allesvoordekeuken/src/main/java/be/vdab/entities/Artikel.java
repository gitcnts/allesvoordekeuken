package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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

	protected Artikel() {
	}

	public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setVerkoopprijs(verkoopprijs);
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

}
