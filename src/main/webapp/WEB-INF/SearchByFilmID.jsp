<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search By Film ID Result</title>
</head>
<body>
<c:choose>
    <c:when test="${! empty film}">
      <ul>
        <li>${film.id}</li>
        <li>${film.title}</li>
        <li>${film.description}</li>
        <li>${film.length}</li>
        <li>${film.rating}</li>
      </ul>
    </c:when>
    <c:otherwise>
      <p>No Film found with that ID, Sorry!</p>
    </c:otherwise>
  </c:choose>

</body>
</html>