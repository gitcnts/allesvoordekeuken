<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://vdab.be/tags" prefix="v"%>
<!doctype html>
<html lang="nl">
<head>
<v:head title="${empty artikel ? 'Artikel zoeken' : artikel.naam}" />
</head>
<body>
	<v:menu />
	<h1>Artikel zoeken op nummer</h1>
	<form>
		<label>Nummer:<span>${fouten.id}</span> 
			<input name="id" value="${param.id}" required autofocus type="number" min="1">
		</label>
		<input type="submit" value="Zoeken">
	</form>
	<c:if test="${not empty param and empty fouten and empty artikel}">
		Artikel niet gevonden
	</c:if>
	<c:if test="${not empty artikel}">
		<h2>${artikel.naam}</h2>
		<dl>
			<dt>Aankoopprijs</dt>
			<dd>&euro; <fmt:formatNumber value="${artikel.aankoopprijs}" minFractionDigits="2" maxFractionDigits="2"/></dd>
			<dt>Verkoopprijs</dt>
			<dd>&euro; <fmt:formatNumber value="${artikel.verkoopprijs}" minFractionDigits="2" maxFractionDigits="2"/></dd>
			<dt>Winst</dt>
			<dd><fmt:formatNumber value="${artikel.winst}" minFractionDigits="2" maxFractionDigits="2"/> %</dd>
		</dl>
	</c:if>
</body>
</html>