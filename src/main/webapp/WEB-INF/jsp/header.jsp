<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:url value="/css/site.css" var="cssURL" />
<link rel="stylesheet" type="text/css" href="${cssURL}">
<title>National Park Geek</title>
</head>

<body>
<div class="header">
<div class="headimage">
    <c:url value="/img/logo.png" var="logoURL" />
    <img src="${logoURL}" id="logo" />
</div>

<div class= "headnav">
<c:url value="/" var="homePageURL" />  
<c:url value="/survey" var="surveyURL"/> 
    <nav>
    		<ul>
    			<li><a href="${homePageURL}">Home</a></li>
    			<li><a href="${surveyURL}">Survey</a></li>
    		</ul>
    </nav>
 </div>
 </div>
 
 <div id="main">
