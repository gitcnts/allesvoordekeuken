package be.vdab.servlets.artikels;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Artikel;
import be.vdab.entities.Artikelgroep;
import be.vdab.entities.FoodArtikel;
import be.vdab.entities.NonFoodArtikel;
import be.vdab.services.ArtikelService;
import be.vdab.services.ArtikelgroepService;
import be.vdab.util.StringUtils;

/**
 * Servlet implementation class ToevoegenServlet
 */
@WebServlet("/artikels/toevoegen.htm")
public class ToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/artikels/toevoegen.jsp";
	private static final String REDIRECT_URL = "%s/artikels/zoekenopnummer.htm?id=%d";
	private final transient ArtikelService artikelService = new ArtikelService();
	private final transient ArtikelgroepService artikelgroepService = new ArtikelgroepService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("artikelgroepen", artikelgroepService.findAll());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new HashMap<>();
		String naam = request.getParameter("naam");
		if (!Artikel.isValidNaam(naam)) {
			fouten.put("naam", "verplicht in te vullen");
		}
		String aankoopprijsString = request.getParameter("aankoopprijs");
		BigDecimal aankoopprijs = null;
		if (StringUtils.isBigDecimal(aankoopprijsString)) {
			aankoopprijs = new BigDecimal(aankoopprijsString);
			if (!Artikel.isValidAankoopprijs(aankoopprijs)) {
				fouten.put("aankoopprijs", "moet minstens 0.01 zijn");
			}
		} else {
			fouten.put("aankoopprijs", "moet minstens 0.01 zijn");
		}
		String verkoopprijsString = request.getParameter("verkoopprijs");
		BigDecimal verkoopprijs = null;
		if (StringUtils.isBigDecimal(verkoopprijsString)) {
			verkoopprijs = new BigDecimal(verkoopprijsString);
			if (!Artikel.isValidVerkoopprijs(verkoopprijs, aankoopprijs)) {
				fouten.put("verkoopprijs", "mag niet kleiner dan aankoopprijs zijn");
			}
		} else {
			fouten.put("verkoopprijs", "mag niet kleiner dan aankoopprijs zijn");
		}
		String soort = request.getParameter("soort");
		long houdbaarheid = 0;
		long garantie = 0;
		if (soort == null) {
			fouten.put("soort", "kies food of non-food");
		} else {
			switch (soort) {
			case "F":
				String houdbaarheidString = request.getParameter("houdbaarheid");
				if (StringUtils.isLong(houdbaarheidString)) {
					houdbaarheid = Long.parseLong(houdbaarheidString);
					if (!FoodArtikel.isValidHoudbaarheid(houdbaarheid)) {
						fouten.put("houdbaarheid", "moet ingevuld zijn bij food artikel");
					}
				} else {
					fouten.put("houdbaarheid", "moet ingevuld zijn bij food artikel");
				}
				break;
			case "NF":
				String garantieString = request.getParameter("garantie");
				if (StringUtils.isLong(garantieString)) {
					garantie = Long.parseLong(garantieString);
					if (!NonFoodArtikel.isValidGarantie(garantie)) {
						fouten.put("garantie", "moet ingevuld zijn bij non-food artikel");
					}
				} else {
					fouten.put("garantie", "moet ingevuld zijn bij non-food artikel");
				}
				break;
			default:
				fouten.put("soort", "kies food of non-food");
				break;
			}
		}
		String artikelgroepId = request.getParameter("artikelgroepen");
		if (artikelgroepId == null) {
			fouten.put("artikelgroepen", "verplicht");
		}
		if (fouten.isEmpty()) {
			Artikel artikel = null;
			Artikelgroep artikelgroep = artikelgroepService.read(Long.parseLong(artikelgroepId)).get();
			if (soort.equals("F")) {
				artikel = new FoodArtikel(naam, aankoopprijs, verkoopprijs, houdbaarheid, artikelgroep);
			} else {
				artikel = new NonFoodArtikel(naam, aankoopprijs, verkoopprijs, garantie, artikelgroep);
			}
			artikelService.create(artikel);
			response.sendRedirect(
					response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath(), artikel.getId())));
		} else {
			request.setAttribute("fouten", fouten);
			request.setAttribute("artikelgroepen", artikelgroepService.findAll());
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
