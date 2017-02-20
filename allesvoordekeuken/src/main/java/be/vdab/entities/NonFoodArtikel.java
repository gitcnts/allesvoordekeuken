package be.vdab.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {

	private static final long serialVersionUID = 1L;

	private long garantie;

	protected NonFoodArtikel() {
		super();
	}

	public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, long garantie, Artikelgroep artikelgroep) {
		super(naam, aankoopprijs, verkoopprijs, artikelgroep);
		setGarantie(garantie);
	}

	public final void setGarantie(long garantie) {
		if (!isValidGarantie(garantie)) {
			throw new IllegalArgumentException("Garantie is verplicht bij non-food artikels");
		}
		this.garantie = garantie;
	}

	public long getGarantie() {
		return garantie;
	}

	public static boolean isValidGarantie(long garantie) {
		return garantie >= 0;
	}

}
