package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Artikel;

public class ArtikelRepository extends AbstractRepository {
	
	public Optional<Artikel> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Artikel.class, id));
	}
	
	public void create(Artikel artikel) {
		getEntityManager().persist(artikel);
	}
	
	public List<Artikel> findArticleByName(String woord) {
		return getEntityManager().createNamedQuery("Artikel.findArticleByName", Artikel.class)
				.setParameter("woord", "%" + woord + "%")
				.getResultList();
	}
	
	public void prijsverhoging(BigDecimal factor) {
		getEntityManager().createNamedQuery("Artikel.prijsverhoging")
		.setParameter("factor", factor)
		.executeUpdate();
	}


}
