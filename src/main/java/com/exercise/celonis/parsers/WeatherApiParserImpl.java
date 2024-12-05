package com.exercise.celonis.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.exercise.celonis.model.Forecast;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.Gson;


@Component
public class WeatherApiParserImpl implements WeatherApiParser {

	final Gson globalGson = new Gson();
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	final String ERROR_MSG = "There was no data found for the specified location!";
	
	public Forecast parseWeatherapiString(String resultString, LocalDate date) {
		
		JsonObject jsonObj = this.globalGson.fromJson(resultString, JsonObject.class);
		
		if(jsonObj == null) {
			
			throw new NullPointerException(ERROR_MSG);
		}
		
		Forecast forecast = new Forecast();
		forecast.setDate(date);
		
		forecast.setLocation(getLocation(jsonObj));
		
		String dateString = date.format(formatter);
		iterateForeCasts(jsonObj, dateString, forecast);
		
		return forecast;
	}
	
	private void iterateForeCasts(JsonObject jsonObject, String dateString, Forecast forecast) {
		
		JsonArray jsonArr = 
			jsonObject.get("forecast").getAsJsonObject().get("forecastday").getAsJsonArray();
		
		for(JsonElement jEl: jsonArr) {
			
			if(isCorrectDate(jEl, dateString)) {
				setDayAttributes(jEl, forecast);
			}
		}
	}
	
	private void setDayAttributes(JsonElement jsonElement, Forecast forecast) {
		
		
		forecast.setMaxTemperate(getDayDoubleAttribute(jsonElement, "maxtemp_c"));
		forecast.setMinTemperate(getDayDoubleAttribute(jsonElement, "mintemp_c"));
		forecast.setHumidity(getDayIntAttribute(jsonElement, "avghumidity"));
		forecast.setPrecipitation(getDayDoubleAttribute(jsonElement, "totalprecip_mm"));
		forecast.setCondition(getConditionString(jsonElement));
	}

	private double getDayDoubleAttribute(JsonElement jsonElement, String attribute) {
		
		return jsonElement.getAsJsonObject().get("day").getAsJsonObject().get(attribute).getAsDouble();
	}
	
	private int getDayIntAttribute(JsonElement jsonElement, String attribute) {
		
		return jsonElement.getAsJsonObject().get("day").getAsJsonObject().get(attribute).getAsInt();
	}
	
	private String getConditionString(JsonElement jsonElement) {
		
		return jsonElement.getAsJsonObject().get("day").getAsJsonObject()
			.get("condition").getAsJsonObject().get("text").getAsString();
	}

	private boolean isCorrectDate(JsonElement jsonElement, String dateString) {
		
		return jsonElement.getAsJsonObject().get("date").getAsString().equals(dateString);
	}

	private String getLocation(JsonObject jsonObject) {
		
		return jsonObject.get("location").getAsJsonObject().get("name").getAsString();
	}
}
