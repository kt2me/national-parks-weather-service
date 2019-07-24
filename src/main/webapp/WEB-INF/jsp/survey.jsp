<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:import url="/WEB-INF/jsp/header.jsp">
	<c:param name="pageTitle" value="National Park Geek" />
</c:import>

<c:url value="survey" var="surveyUrl"/>

<div class="form">
<form:form action="${surveyUrl}" method="POST" modelAttribute="survey">
<h2>Vote for your favorite park:</h2>

	<div class="emailEntry">
	<label for="email">Email:</label>
	<input type="text" name="email"/>
	</div>
	
	<div class="parkEntry">
	<select name="parkChoice">
		<c:forEach var="item" items="${parks}">
			<option value="${item.parkCode}">${item.parkName}</option>
		</c:forEach>
	</select>
	</div>
	
	<div class="stateEntry">
	<select name="state">
	<option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">District Of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
</select>						
	</div>

<div class="activity">
	<label for="activity">Inactive</label>
	<input type="radio" value="inactive" name="activity"/>
	<label for="activity">Sedentary</label>
   	<input type="radio" value="sedentary" name="activity"/> 
   	<label for="activity">Active</label>
   	<input type="radio" value="active" name="activity" checked/>
   	<label for="activity">Extremely Active</label>
   	<input type="radio" value="extremely active" name="activity"/> 
   	
</div>
	
	<input type="submit" value="Submit!" />
</form:form>
</div>

	<c:import url="/WEB-INF/jsp/footer.jsp" />
