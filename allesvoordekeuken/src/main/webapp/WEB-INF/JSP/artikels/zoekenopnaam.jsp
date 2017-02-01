<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://vdab.be/tags" prefix="v"%>
<!doctype html>
<html lang="nl">
<head>
<v:head title="Artikels zoeken op naam" />
</head>
<body>
	<v:menu />
	<h1>Artikel zoeken op naam</h1>
	<form>
		<label>Woord:<span>${fouten.woord}</span> 
			<input name="woord" value="${param.woord}" required autofocus type="search">
		</label>
		<input type="submit" value="Zoeken">
	</form>
	<c:if test="${not empty param and empty fouten and empty artikels}">
		Geen artikels gevonden
	</c:if>
	<c:if test="${not empty artikels}">
		<table>
			<thead>
				<tr>
					<th>Nummer</th>
					<th class="links">Naam</th>
					<th>Aankoopprijs</th>
					<th>Verkoopprijs</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items='${artikels}' var='artikel'>
					<tr>
						<td class="rechts">${artikel.id}</td>
						<td>${artikel.naam}</td>
						<td class="rechts"><fmt:formatNumber value='${artikel.aankoopprijs}'
								minFractionDigits='2' maxFractionDigits='2' /></td>
						<td class="rechts"><fmt:formatNumber value='${artikel.verkoopprijs}'
								minFractionDigits='2' maxFractionDigits='2' /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>