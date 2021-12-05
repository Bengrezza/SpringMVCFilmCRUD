<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Film</title>
</head>
<body>
	<h1>Add a film to the database</h1>
	<form:form action="addFilmNew.do" method="POST" modelAttribute="film">
		<form:label path="title">Title:</form:label>
		<form:input path="title" />
		<form:errors path="title" />
		<br />

		<form:label path="description">Description:</form:label>
		<form:input path="description" />
		<form:errors path="description" />
		<br />

		<form:label path="releaseYear">Release Year:</form:label>
		<form:input path="releaseYear" />
		<form:errors path="releaseYear" />
		<br />

		<form:label path="languageID">Language:</form:label>
		<form:select path="languageID">Language:
			<form:option value="1">English</form:option>
			<form:option value="2">Italian</form:option>
			<form:option value="3">Japanese</form:option>
			<form:option value="4">Mandarin</form:option>
			<form:option value="5">French</form:option>
			<form:option value="6">German</form:option>
		</form:select>
		<br />

		<form:label path="rentalDuration">Rental Duration:</form:label>
		<form:input path="rentalDuration" />
		<form:errors path="rentalDuration" />
		<br />

		<form:label path="length">Length:</form:label>
		<form:input path="length" />
		<form:errors path="length" />
		<br />

		<form:label path="replacementCost">Replacement Cost:</form:label>
		<form:input path="replacementCost" />
		<form:errors path="replacementCost" />
		<br />

		<form:label path="rating">Rating:</form:label>
		<form:select path="rating">Rating:
			<form:option value="G">G</form:option>
			<form:option value="PG">PG</form:option>
			<form:option value="PG13">PG-13</form:option>
			<form:option value="R">R</form:option>
			<form:option value="NC17">NC-17</form:option>
			<form:option value="n/a">n/a</form:option>
		</form:select>
		<br />

		<form:label path="specialFeatures">Special Features:</form:label>
		<br>
		<form:select multiple="true" path="specialFeatures">Special Features:
		   <form:option value="Trailers">Trailers</form:option>
			<form:option value="Commentaries">Commentaries</form:option>
			<form:option value="Deleted Scenes">Deleted Scenes</form:option>
			<form:option value="Behind The Scenes">Behind the Scenes</form:option>
			<form:option value="n/a">n/a</form:option>
		</form:select>
		<br />
		
		<input type="submit" value="Submit" />
	</form:form>

</body>
</html>