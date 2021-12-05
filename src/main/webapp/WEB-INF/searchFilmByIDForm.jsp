<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Film Search</title>
</head>
<body>
	<h3>Search Film by ID</h3>
	<form action="searchFilmByID.do" method="GET">
		Film ID:
		<input type="text" name="filmID" size="5"/> 
		<input type="submit" value="Get Film Data" />
	</form>
	<br>
	<h3>Search Film by keyword in Film title or description</h3>
	<form action="searchKeyword.do" method="GET">
		Enter keyword:
		<input type="text" name="keyword" size="25"/> 
		<input type="submit" value="Search Keyword" />
	</form>
</body>
</html>