package com.techelevator.model;


public class Forecast {
private String parkCode;
private int day;
private int low;
private int high;
private String weather;
private boolean isCelcius;
public String getParkCode() {
	return parkCode;
}
public void setParkCode(String parkCode) {
	this.parkCode = parkCode;
}
public int getDay() {
	return day;
}
public void setDay(int day) {
	this.day = day;
}
public int getLow() {
	return low;
}
public void setLow(int low) {
	this.low = low;
}
public int getHigh() {
	return high;
}
public void setHigh(int high) {
	this.high = high;
}
public String getWeather() {
	return weather;
}
public void setWeather(String weather) {
	this.weather = weather;
}

public String getWeatherImage(){
if(getWeather().equals("partly cloudy")) {
	return "img/weather/partlyCloudy.png";
} 
String symbol = "img/weather/" + getWeather() +".png";
return symbol;

}

public int getTempInCelcius(int temp) {
	double newTemp = Math.floor(temp - 32 * (0.55));
	return (int)newTemp;
}

public boolean isCelcius() {
	return isCelcius;
}

public void setCelcius(boolean isCelcius) {
	this.isCelcius = isCelcius;
}

public String weatherWarning(String weather) {
	String warningStatement = "";
	if(weather.equals("snow")) {
		warningStatement += "Don't forget to pack snowshoes!";
	}
	if(weather.equals("rain")) {
		warningStatement += "Don't forget to pack rain gear and waterproof shoes!";
	}
	if(weather.equals("thunderstorms")) {
		warningStatement += "Seek shelter and avoid hiking.";
	}
	if(weather.equals("sunny")) {
		warningStatement += "Don't forget to pack sunscreen.";
	}if (getHigh()-getLow() > 20) {
		warningStatement += " Wear breathable layers.";
	}if (getHigh()>75) {
		warningStatement += " Pack an extra gallon of water.";
	}if(getLow()<20) {
		warningStatement += " Below freezing temperatures can be very dangerous. Please proceed with caution.";
	}
	return warningStatement;
}


}
