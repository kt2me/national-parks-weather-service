package com.techelevator;

import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Forecast;
import com.techelevator.model.Park;
import com.techelevator.model.Survey;
import com.techelevator.reader.JDBCParkDAO;
import com.techelevator.reader.ParkDAO;



public class JDBCParksIntegrationTest extends DAOIntegrationTest{
	
	private ParkDAO dao;
	private JdbcTemplate jdbcTemplate;
	private Park testPark1;
	private Park testPark2;
	private Forecast testForecast1;
	private Survey testSurvey1;
	private Survey testSurvey2;
	private Survey testSurvey3;


	
	
	@Before
	public void setup() {
		dao = new JDBCParkDAO(getDataSource());
		jdbcTemplate = new JdbcTemplate(getDataSource());
		testPark1 = createPark("P1", "Park1", "Ohio", 2000, 999, 500, 400, "temperate", 1999, 34000, "This is a fake park", "Eric", "A lovely park", 12, 24);
		testPark2 = createPark("P2", "Park2", "Florida", 3000, 888, 600, 500, "swamp", 1989, 35000, "Where all the crazies are", "Katie", "A swampy park", 15, 66);
		testForecast1 = createForecast("P1", 3, 72, 85, "Sunnny");
		emptySurveyResults();



	}

	@Test
	public void get_all_parks() {
		//arrange
		List<Park>startingList = dao.getAllParks();
		int startingSize = startingList.size();
	

		addParksToDatabase(testPark1);
		addParksToDatabase(testPark2);

		//ACT
		List<Park> newParkList = dao.getAllParks();
		//Assert
		Assert.assertEquals(startingSize + 2, newParkList.size());
	}
	@Test
	public void display_park_information() {
		addParksToDatabase(testPark1);
		//ACT
		Park newPark = dao.getPark("P1");
		String parkArea = newPark.getParkName();
		//Assert
		Assert.assertEquals(parkArea, "Park1");
	}
	
	@Test
	public void display_forecast() {
		addParksToDatabase(testPark1);
		addForecastToDatabase(testForecast1);
		ArrayList <Forecast> newForecast = dao.readWeather("P1");
		int forecastHigh = newForecast.get(0).getHigh();
		Assert.assertEquals(forecastHigh, 85);
	}
	
	@Test
	public void save_survey(){
		addParksToDatabase(testPark1);
		testSurvey1 = createSurvey("kt2me@gmail.com","P1", "OH", "active");
		dao.saveSurveyResults(testSurvey1);
		
		Survey submitted = getSurveyByEmail("kt2me@gmail.com");
		String state = submitted.getState();
		Assert.assertEquals(state, "OH");
	}
	
	@Test
	public void survey_shows_winning_park(){
		addParksToDatabase(testPark1);
		addParksToDatabase(testPark2);
		
		testSurvey1 = createSurvey("kt2me@gmail.com","P1", "OH", "active");
		dao.saveSurveyResults(testSurvey1);
		testSurvey2 = createSurvey("hi@gmail.com","P1", "AZ", "active");
		dao.saveSurveyResults(testSurvey2);
		testSurvey3 = createSurvey("howdy@gmail.com","P2", "AZ", "active");
		dao.saveSurveyResults(testSurvey3);

		ArrayList <Park> winningParks = dao.getSurveyResults();
		String winner = winningParks.get(0).getParkName();
		
		Assert.assertEquals(winner, "Park1");
	}
	
	
	
	private void addParksToDatabase(Park park){
		String sql = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail, numberofcampsites, climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource, parkdescription, entryfee, numberofanimalspecies) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, park.getParkCode(), park.getParkName(), park.getState(), park.getAcreage(), park.getElevation(), park.getMilesOfTrail(), park.getNumberOfCampsites(), park.getClimate(), park.getYearFounded(), park.getAnnualVisitors(), park.getQuote(), park.getQuoteSource(), park.getDescription(), park.getEntryFee(), park.getNumberOfSpecies());
	}
	
	private void addForecastToDatabase(Forecast forecast){
		String sql = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sql, forecast.getParkCode(), forecast.getDay(), forecast.getLow(), forecast.getHigh(), forecast.getWeather());
	}
	
	private Park createPark(String parkCode, String parkName, String state, long acreage, int elevation, double milesOfTrail, int numberOfCampsites, String climate, int yearFounded, int annualVistors, String quote, String quoteSource, String description, int entryFee, int numberOfSpecies) {
		Park thePark = new Park();
		thePark.setParkCode(parkCode);
		thePark.setParkName(parkName);
		thePark.setState(state);
		thePark.setAcreage(acreage);
		thePark.setElevation(elevation);
		thePark.setMilesOfTrail(milesOfTrail);
		thePark.setClimate(climate);
		thePark.setYearFounded(yearFounded);
		thePark.setAnnualVisitors(annualVistors);
		thePark.setQuote(quote);
		thePark.setQuoteSource(quoteSource);
		thePark.setDescription(description);
		thePark.setEntryFee(entryFee);
		thePark.setNumberOfSpecies(numberOfSpecies);
		
		return thePark;	
	}
	
	private Forecast createForecast(String parkCode, int day, int low, int high, String weather) {
		Forecast theForecast = new Forecast();
		theForecast.setParkCode(parkCode);
		theForecast.setDay(day);
		theForecast.setLow(low);
		theForecast.setHigh(high);
		theForecast.setWeather(weather);
		return theForecast;	
	}
	
	private Survey createSurvey(String email, String parkChoice, String state, String activity) {
		Survey survey= new Survey();
		survey.setEmail(email);
		survey.setParkChoice(parkChoice);
		survey.setState(state);
		survey.setActivity(activity);
		
		return survey;
	}
	
	public void emptySurveyResults() {
		String sql = "TRUNCATE TABLE survey_result";		
		jdbcTemplate.update(sql);
	}
	
	public Survey getSurveyByEmail(String email) {
		String sql = "select * from survey_result WHERE emailaddress = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, email);
		
		Survey newSurvey = new Survey();
		if(result.next()) {
		newSurvey = mapRowToSurvey(result);
		}
		return newSurvey;
	}
	
	private Survey mapRowToSurvey(SqlRowSet result) {
		Survey s = new Survey();
		s.setEmail(result.getString("emailaddress"));
		s.setParkChoice(result.getString("parkcode"));
		s.setState(result.getString("state"));
		s.setActivity(result.getString("activitylevel"));
		return s;
	}
}
