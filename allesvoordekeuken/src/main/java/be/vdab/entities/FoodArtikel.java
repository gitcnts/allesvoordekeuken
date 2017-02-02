package be.vdab.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {

	private static final long serialVersionUID = 1L;

	private long houdbaarheid;

	protected FoodArtikel() {
		super();
	}

	public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, long houdbaarheid) {
		super(naam, aankoopprijs, verkoopprijs);
		setHoudbaarheid(houdbaarheid);
	}

	public final void setHoudbaarheid(long houdbaarheid) {
		if (!isValidHoudbaarheid(houdbaarheid)) {
			throw new IllegalArgumentException("Houdbaarheid is verplicht bij food artikels");
		}
		this.houdbaarheid = houdbaarheid;
	}

	public long getHoudbaarheid() {
		return houdbaarheid;
	}

	public static boolean isValidHoudbaarheid(long houdbaarheid) {
		return houdbaarheid >= 1;
	}

}
