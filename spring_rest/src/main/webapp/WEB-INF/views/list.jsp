<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  
  <script type="text/javascript" src="../resources/assets/js/jquery-1.7.2.min.js"></script>
  <script>
	var jQuery = jQuery.noConflict();
	jQuery(document).ready(function() {
		
		 console.log('onReady called');
		alert("Jquery Ready!");
	});
  </script>
</head>
<body>
	<h1>Spring 3 MVC REST web service</h1>
 
	<h2>Movie Name : ${movie}</h2>	ddd
</body>
</html>