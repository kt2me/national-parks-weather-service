package com.techelevator.reader;

import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Forecast;
import com.techelevator.model.Park;
import com.techelevator.model.Survey;

@Component
public class JDBCParkDAO implements ParkDAO{
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCParkDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public ArrayList<Park> getAllParks() {

		String sql = "select * from park";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		ArrayList<Park> parks = new ArrayList<Park>();
		
		while (result.next()) {
			Park park = mapRowToPark(result);
			parks.add(park);
		}
		
		return parks;
		
	}
	
	@Override
	public Park getPark(String code){
		String sql = "select * from park WHERE parkcode = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, code);
		Park newPark = new Park();
		if(result.next()) {
		newPark = mapRowToPark(result);
		}
		return newPark;
	}
	
	
	private Park mapRowToPark(SqlRowSet result) {
		Park p = new Park();
		p.setParkCode(result.getString("parkcode"));
		p.setParkName(result.getString("parkname"));
		p.setState(result.getString("state"));
		p.setAcreage(result.getLong("acreage"));
		p.setAnnualVisitors(result.getInt("annualvisitorcount"));
		p.setElevation(result.getInt("elevationinfeet"));
		p.setMilesOfTrail(result.getDouble("milesoftrail"));
		p.setNumberOfCampsites(result.getInt("numberofcampsites"));
		p.setClimate(result.getString("climate"));
		p.setYearFounded(result.getInt("yearfounded"));
		p.setQuote(result.getString("inspirationalquote"));
		p.setQuoteSource(result.getString("inspirationalquotesource"));
		p.setDescription(result.getString("parkdescription"));
		p.setEntryFee(result.getInt("entryfee"));
		p.setNumberOfSpecies(result.getInt("numberofanimalspecies"));
		
		return p;
	}
	
	@Override
	public ArrayList<Forecast> readWeather(String code) {

		String sql = "select * from weather WHERE parkcode= ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, code);
		ArrayList<Forecast> forecasts = new ArrayList<Forecast>();
				
		while (result.next()) {
		Forecast forecast = mapRowToForecast(result);
			forecasts.add(forecast);
				}
				return forecasts;	
			}
	
	private Forecast mapRowToForecast(SqlRowSet result) {
		Forecast f = new Forecast();
		f.setParkCode(result.getString("parkcode"));
		f.setDay(result.getInt("fivedayforecastvalue"));
		f.setLow(result.getInt("low"));
		f.setHigh(result.getInt("high"));
		f.setWeather(result.getString("forecast"));
		return f;
	}
	
	@Override
	public void saveSurveyResults(Survey survey) {
		String sql = "INSERT INTO survey_result (surveyid, parkCode, emailaddress, state, activitylevel) VALUES (DEFAULT, ?, ?, ?, ?)";		
		jdbcTemplate.update(sql, survey.getParkChoice(), survey.getEmail(), survey.getState(), survey.getActivity());
	}
	
	@Override
	public ArrayList<Park> getSurveyResults(){
		String sql = "SELECT count (parkcode) as votes, parkcode FROM survey_result GROUP BY parkcode ORDER BY votes DESC";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		ArrayList<Park>popularParks = new ArrayList<Park>();
		while(result.next()) {
			Park park = getPark(result.getString("parkcode"));
			popularParks.add(park);
		}
		return popularParks;
	}
}
