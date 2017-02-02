<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://vdab.be/tags" prefix="v" %>
<!doctype html>
<html lang="nl">
<head>
<v:head title="Artikel toevoegen" />
</head>
<body>
	<v:menu />
	<h1>Artikel toevoegen</h1>
	<form method="post" id="toevoegform">
		<label>Naam:<span>${fouten.naam}</span> <input
			name="naam" value="${param.naam}" autofocus required></label>
		<label>Aankoopprijs:<span>${fouten.aankoopprijs}</span>
			<input name="aankoopprijs" value="${param.aankoopprijs}" required type="number"
			min="0.01" step="0.01"></label>
		<label>Verkoopprijs:<span>${fouten.verkoopprijs}</span>
			<input name="verkoopprijs" value="${param.verkoopprijs}" required type="number"
			min="0.01" step="0.01"></label>
		<div>
			<label> <input type="radio" name="soort" value="F" id="food" ${param.soort=="F" ? "checked" : ""}> 
					Food <span>${fouten.soort}</span> 
			</label>
		</div>
		<label>Houdbaarheid:<span>${fouten.houdbaarheid}</span>
			<input name="houdbaarheid" value="${param.houdbaarheid}" type="number" id="houdbaarheid" min="1">
		</label>
		<div>
			<label> <input type="radio" name="soort" value="NF" id="nonfood" ${param.soort=="NF" ? "checked" : ""}>
				Non-Food
			</label>
		</div>
		<label>Garantie:<span>${fouten.garantie}</span>
			<input name="garantie" value="${param.garantie}" type="number" id="garantie" min="0">
		</label>
		<input type="submit" value="Toevoegen" id="toevoegknop">
	</form>
	<script>
		document.getElementById("toevoegform").onsubmit = function() {
			document.getElementById("toevoegknop").disabled = true;
		};
		document.getElementById("food").onclick = enableDisableInputs;
		document.getElementById("nonfood").onclick = enableDisableInputs;
		enableDisableInputs();
		function enableDisableInputs() {
			document.getElementById("houdbaarheid").disabled = !document
					.getElementById("food").checked;
			document.getElementById("garantie").disabled = !document
					.getElementById("nonfood").checked;
		}
	</script>
</body>
</html>
