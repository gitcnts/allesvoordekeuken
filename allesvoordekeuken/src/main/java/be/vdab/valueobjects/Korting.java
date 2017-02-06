package be.vdab.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Korting implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int vanafAantal;
	private BigDecimal kortingsPercentage;
	
	public final int getVanafAantal() {
		return vanafAantal;
	}
	public final BigDecimal getKortingsPercentage() {
		return kortingsPercentage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vanafAantal;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Korting)) {
			return false;
		}
		Korting korting = (Korting) obj;
		return vanafAantal == korting.vanafAantal;
	}
	
	@Override
	public String toString() {
		return "Korting [vanafAantal=" + vanafAantal + ", kortingsPercentage=" + kortingsPercentage + "]";
	}
	
}
