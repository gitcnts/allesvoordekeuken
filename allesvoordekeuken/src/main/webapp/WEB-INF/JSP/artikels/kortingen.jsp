<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Artikelkortingen' />
</head>
<body>
	<v:menu />
	<h1>Artikelkortingen</h1>
	<ul>
		<c:forEach var='artikel' items='${artikels}'>
			<c:url value='' var='kortingURL'>
				<c:param name='id' value="${artikel.id }" />
			</c:url>
			<li><a href="<c:out value='${kortingURL}'/>">${artikel.naam }</a></li>
		</c:forEach>
	</ul>
	<c:if test='${not empty artikel}'>
		<h2>Kortingen ${artikel.naam }</h2>
		<table>
			<thead>
				<tr>
					<th>Vanaf aantal</th>
					<th>% korting</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='korting' items='${artikel.kortingen}'>
					<tr class="rechts">
						<td>${korting.vanafAantal }</td>
						<td><fmt:formatNumber value='${korting.kortingsPercentage}'
								minFractionDigits='2' maxFractionDigits='2' /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>