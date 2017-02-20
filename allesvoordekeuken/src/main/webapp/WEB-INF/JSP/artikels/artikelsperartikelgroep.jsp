<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Artikels per artikelgroep' />
</head>
<body>
	<v:menu />
	<h1>Artikels per artikelgroep</h1>
	<ul class='zonderbolletjes'>
		<c:forEach items='${artikelgroepen}' var='artikelgroep'>
			<c:url value='' var='url'>
				<c:param name='id' value='${artikelgroep.id}' />
			</c:url>
			<li><a href='${url}'>${artikelgroep.naam}</a></li>
		</c:forEach>
	</ul>
	<c:if test='${not empty artikelgroep}'>
		<h2>${artikelgroep.naam}</h2>
		<table>
			<thead>
				<tr>
					<th>Nummer</th>
					<th>Naam</th>
					<th>Verkoopprijs</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items='${artikelgroep.artikels}' var='artikel'>
					<tr>
						<td>${artikel.id}</td>
						<td>${artikel.naam}</td>
						<td><fmt:formatNumber value='${artikel.verkoopprijs}'
								minFractionDigits='2' maxFractionDigits='2' /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>