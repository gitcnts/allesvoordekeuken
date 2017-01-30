package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artikels")
public class Artikel implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final BigDecimal HONDERD = BigDecimal.valueOf(100);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;

	public long getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	public BigDecimal getAankoopprijs() {
		return aankoopprijs;
	}
	public BigDecimal getVerkoopprijs() {
		return verkoopprijs;
	}
	
	public BigDecimal getWinst() {
		if (aankoopprijs != null && aankoopprijs != BigDecimal.ZERO) {
			return verkoopprijs.subtract(aankoopprijs).divide(aankoopprijs, 4, RoundingMode.HALF_UP)
					.multiply(HONDERD);
		} else {
			return BigDecimal.ZERO;
		}
	}

}
