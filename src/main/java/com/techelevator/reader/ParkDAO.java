package com.techelevator.reader;

import java.util.ArrayList;
import com.techelevator.model.Forecast;
import com.techelevator.model.Park;
import com.techelevator.model.Survey;

public interface ParkDAO {
	public ArrayList<Park> getAllParks();	
	public Park getPark(String code);
	public ArrayList<Forecast> readWeather(String code);
	public void saveSurveyResults(Survey survey);
	public ArrayList<Park> getSurveyResults();
}
