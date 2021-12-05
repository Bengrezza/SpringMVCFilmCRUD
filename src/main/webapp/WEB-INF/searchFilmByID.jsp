<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Film Results</title>
</head>
<body>
	<h1>Search Film Results</h1>
	<div class="container">
		<c:out value="${filmId }" />
		<c:if test="${empty film }">No film found</c:if>
		<c:if test="${not empty film }">
			<h2>Title: ${film.title }</h2>
			<table>
				<tr>
					<td>Film ID:</td>
					<td>${film.filmId}</td>
				</tr>
				<tr>
					<td>Film Description:</td>
					<td>${film.description}</td>
				</tr>
				<tr>
					<td>Release Year:</td>
					<td>${film.releaseYear}</td>
				</tr>
				<tr>
					<td>Rating:</td>
					<td>${film.rating}</td>
				</tr>
				<c:choose>
					<c:when test="${film.languageID eq 0 }">
						<tr>
							<td>Language:</td>
							<td>${film.language}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>Language ID:</td>
							<td>${film.languageID}</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td>Special Features:</td>
					<td>${film.specialFeatures}</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${not empty film.categoryFilm }">
							<td>Category:</td>
							<td>${film.categoryFilm}</td>
						</c:when>
						<c:otherwise>
							<td>Category:</td>
							<td>No Category</td>

						</c:otherwise>


					</c:choose>
				</tr>
				<tr>
					<td>Actors:</td>
					<td><c:if test="${empty film.actors }">No actors found</c:if>
						<c:if test="${not empty actors}">${actors}</c:if></td>
				</tr>

			</table>
			<form action="getFilmFields.do" method="GET">
				<button type="submit" name="filmID" value="${film.filmId}">Update
					Film Details</button>
			</form>
			<form action="deleteFilm.do" method="POST">
				<button type="submit" name="filmID" value="${film.filmId}">Delete
					Film</button>
			</form>

		</c:if>

		<br>
		<p>
			<a href="home.do" class="btn btn-secondary" role="button">Back to
				Home</a>
		</p>
	</div>
</body>
</html>