<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<c:import url="/WEB-INF/jsp/header.jsp">
	<c:param name="pageTitle" value="National Park Geek" />
</c:import>

<div class="parkInfo">
<div class="parkImage">
	<c:set var="imgToConvert" value="${park.parkCode}"/>
 	   <c:set var="img" value="${fn:toLowerCase(imgToConvert)}"/>
	<c:url var="imgUrl" value="img/parks/${img}.jpg"/>
	<img class="shadow" src="${imgUrl}"/>
</div>

<div class="title">
<h1 class="parkName"><c:out value="${park.parkName}"/></h1>
<h2 class="state"><c:out value="${park.state}"/></h2>
</div>

<div id="parkText">
<div class="info details">
	<p class="acreage"><strong>Size:</strong> <fmt:formatNumber type="number" value="${park.acreage}"/> square acres</p>
	<p class="elevation"> <strong>Elevation:</strong> <fmt:formatNumber type="number" value="${park.elevation}"/> feet</p>
	<p class="trail"> <strong>Miles of trail:</strong> <c:out value="${park.milesOfTrail}"/></p>
	<p class="campsites"> <strong>Number of campsites:</strong> <c:out value="${park.numberOfCampsites}"/></p>
	<p class="climate"> <strong>Climate:</strong> <c:out value="${park.climate}"/> </p>
	<p class="founded"> <strong>Year Founded:</strong> <c:out value="${park.yearFounded}"/></p>
	<p class="visitors"> <strong>Number of Visitors per Year: </strong><fmt:formatNumber type="number" value="${park.annualVisitors}"/></p>
	<p class="fee"> <strong>Entry Fee: </strong>$<c:out value="${park.entryFee}"/></p>
	<p class="species"> <strong>Number of Species:</strong> <fmt:formatNumber type="number" value="${park.numberOfSpecies}"/></p>	
</div>	
<div class="inspiration details">
	<p class="description"> <c:out value="${park.description}"/></p>
	<p class="quote"> <c:out value="${park.quote}"/></p>
	<p class="author"> <i>-<c:out value="${park.quoteSource}"/></i></p>
</div>	
</div>
<c:url value="/parkDetails" var="parkDetailsUrl"/>
<form action="${parkDetailsUrl}" method="POST">
   <input type="radio" value="false" name="temp" ${(!tempType) ? "checked": ''}/>
   Farenheit
   <input type="radio" value="true" name="temp" ${(tempType) ? "checked": ''}/> 
   Celcius
	<input type="hidden" value="${park.parkCode}" name="code"/> 
	<button type="submit"> Change </button>
</form> 
</div>



<div class="parkWeatherInfo">
	<c:forEach var="item" items="${forecast}">
		<c:choose>
			<c:when test="${item.day==1}">
				<div class="presentDay day">
					<c:url var="weatherImg" value="${item.weatherImage}" />
					<img src="${weatherImg}" />
					<div id="presentDayTemp">
					<c:choose>
						<c:when test="${tempType==true}">
							<c:set value="${item.low}" var="itemLow" />
							<c:set value="${item.getTempInCelcius(itemLow)}" var="lowTemp" />
							<div><h4>Low:<c:out value="${lowTemp}" />&#8451</h4></div>
							
							<c:set value="${item.high}" var="itemHigh" />
							<c:set value="${item.getTempInCelcius(itemHigh)}" var="highTemp" />
							<div><h4>High:<c:out value="${highTemp}" />&#8451</h4></div>
						</c:when>
						<c:otherwise>
						<h4>Low:<c:out value="${item.low}" />&#8457</h4>	
						<h4>High:<c:out value="${item.high}" />&#8457</h4>
						</c:otherwise>
						</c:choose>
					</div>
					<c:set value="${item.weather}" var="itemWeather" />
					<p><c:out value="${item.weatherWarning(itemWeather)}" /></p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="futureDay day">
				<c:url var="weatherImg" value="${item.weatherImage}" />
					<img src="${weatherImg}" />
					<div id="temp">
					<c:choose>
						<c:when test="${tempType==true}">
							<c:set value="${item.low}" var="itemLow" />
							<c:set value="${item.getTempInCelcius(itemLow)}" var="lowTemp" />
							<div><h4>Low:<c:out value="${lowTemp}"/>&#8451</h4></div>
							<c:set value="${item.high}" var="itemHigh" />
							<c:set value="${item.getTempInCelcius(itemHigh)}" var="highTemp" />
							<div><h4>High:<c:out value="${highTemp}" />&#8451</h4></div>
						</c:when>
						<c:otherwise>
							<div><h4>Low:<c:out value="${item.low}" />&#8457</h4></div>
							<div><h4>High:<c:out value="${item.high}"/>&#8457</h4></div>
						</c:otherwise>
					</c:choose>
					</div>
					<c:set value="${item.weather}" var="itemWeather"/>
					<p><c:out value="${item.weatherWarning(itemWeather)}" /></p>
					</div>
			</c:otherwise>
</c:choose>
</c:forEach>
</div>


<c:import url="/WEB-INF/jsp/footer.jsp" />
