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

import be.vdab.services.ArtikelService;
import be.vdab.util.StringUtils;


/**
 * Servlet implementation class Prijsverhoging
 */
@WebServlet("/artikels/prijsverhoging.htm")
public class Prijsverhoging extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/artikels/prijsverhoging.jsp";
	private final transient ArtikelService artikelService = new ArtikelService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new HashMap<>();
		String percentageString = request.getParameter("percentage");
		BigDecimal percentage = null;
		if (StringUtils.isBigDecimal(percentageString)) {
			percentage = new BigDecimal(percentageString);
			if (percentage.compareTo(BigDecimal.ZERO) <= 0) {
				fouten.put("percentage", "typ een positief getal of 0");
			}
		} else {
			fouten.put("percentage", "typ een positief getal of 0");
		}
		if (fouten.isEmpty()) {
			artikelService.prijsverhoging(percentage);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
