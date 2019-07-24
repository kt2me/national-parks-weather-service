<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:import url="/WEB-INF/jsp/header.jsp">
	<c:param name="pageTitle" value="National Park Geek" />
</c:import>


<c:forEach var="park" items="${parks}">
<div class="individualPark">
<div class=parkImage>
<c:url var="detailsUrl" value="parkDetails">
		<c:param name="code" value="${park.parkCode}"/>
</c:url>
	<c:set var="imgToConvert" value="${park.parkCode}"/>
 	   <c:set var="img" value="${fn:toLowerCase(imgToConvert)}"/>
	<c:url var="imgUrl" value="img/parks/${img}.jpg"/>
	<a href="${detailsUrl}"><img class="shadow" src="${imgUrl}"/></a>
</div>
<div class="generalText">
	<h1 class="parkName headline">
	<c:out value="${park.parkName}"/>
	</h1>
	<h3> <c:out value="${park.state}"/></h3>
	<p> <c:out value="${park.description}"/></p>
</div>
</div>
</c:forEach>

	<c:import url="/WEB-INF/jsp/footer.jsp" />

