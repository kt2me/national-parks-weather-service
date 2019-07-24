package com.techelevator.npgeek;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.techelevator.model.Forecast;
import com.techelevator.model.Park;
import com.techelevator.model.Survey;
import com.techelevator.reader.ParkDAO;

@SessionAttributes({"tempType"})

@Controller
public class ParkController {


	@Autowired
	private ParkDAO reader;
	
	
	@RequestMapping("/")
	public String displayHomePage(ModelMap map) {

		ArrayList<Park> np = reader.getAllParks();

		map.addAttribute("parks", np);
		return "homepage";
	}
	
	@RequestMapping(path="/parkDetails", method=RequestMethod.GET)
	public String showSingleParkDetail(@RequestParam String code, ModelMap map) {
		
		Park park = reader.getPark(code);
		map.addAttribute("park", park);

		ArrayList<Forecast> forecast = reader.readWeather(code);
		map.addAttribute("forecast", forecast);

		if(!map.containsAttribute("tempType")) {
			boolean tempType = forecast.get(0).isCelcius();
			map.addAttribute("tempType", tempType);
		}
		
		return "parkDetails";
	}
	
	@RequestMapping(path="/parkDetails", method=RequestMethod.POST)
	public String getForecastInCelcius(@RequestParam boolean temp, ModelMap map, @RequestParam String code) {

		System.out.println(temp);
		
		Park park = reader.getPark(code);
		map.addAttribute("park", park);

		ArrayList<Forecast> forecast = reader.readWeather(code);
		map.addAttribute("forecast", forecast);

		
		if(!map.containsAttribute("tempType")) {
			boolean tempType = forecast.get(0).isCelcius();
			map.addAttribute("tempType", tempType);
		}
		map.addAttribute("tempType", temp);
		
		return "parkDetails";
	}

	
	@RequestMapping(path="/survey", method=RequestMethod.GET)
	public String displaySurveyPage(ModelMap map) {
		
		ArrayList<Park> np = reader.getAllParks();
		map.addAttribute("parks", np);
		return "survey";
	}
	
	@RequestMapping(path="/survey", method=RequestMethod.POST)
	public String getSurveyAnswers(Survey newSurvey, ModelMap map) {
		
		reader.saveSurveyResults(newSurvey);
		
		return "redirect:/surveyResults";
	}
	
	@RequestMapping("/surveyResults")
	public String displaySurveyResultsPage(ModelMap map) {
		
		ArrayList<Park> winningParks = reader.getSurveyResults();
		map.addAttribute("winningParks", winningParks);
		return "surveyResults";
	}
	
	
	
	
	
	
}

