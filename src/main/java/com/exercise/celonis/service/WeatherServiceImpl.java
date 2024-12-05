package com.exercise.celonis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exercise.celonis.dao.ForecastRepository;
import com.exercise.celonis.parsers.WeatherApiParser;
import com.exercise.celonis.model.Forecast;
import com.exercise.celonis.model.ForecastID;

@Service
public class WeatherServiceImpl implements WeatherService {

	final String KEY = "5004ea89d9c64b5e8cc164827240412";
	final String ENDPOINT = "https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=2&aqi=no&alerts=no";
	final LocalDate CURRENT_DATE = LocalDate.now();
	final LocalDate TOMORROWS_DATE = CURRENT_DATE.plusDays(1);
	
	final String NA_ERROR_MSG = 
		"There is no information available for %s on today's or tomorrow's date";
	
	private ForecastRepository forecastRepo;
	private WeatherApiParser weatherApiParser;
	
	public WeatherServiceImpl(ForecastRepository forecastRepo, WeatherApiParser weatherApiParser) {
		this.forecastRepo = forecastRepo;
		this.weatherApiParser = weatherApiParser;
	}

	public List<Forecast> persistForeCast(String location) throws IOException {
		
		List<Forecast> resultList = new ArrayList<>();
		
		ForecastID fcIDToday = new ForecastID(location, CURRENT_DATE);
		ForecastID fcIDTomorrow = new ForecastID(location, TOMORROWS_DATE);
		
		if(!(forecastRepo.existsById(fcIDToday) && forecastRepo.existsById(fcIDTomorrow))) {
			
			String forecastJSON = getForecast(location);

			if(!forecastRepo.existsById(fcIDToday)) {
				
				Forecast newFC = weatherApiParser.parseWeatherapiString(forecastJSON, CURRENT_DATE);
				this.forecastRepo.save(newFC);
				resultList.add(newFC);
			}
			
			if(!forecastRepo.existsById(fcIDTomorrow)) {
				
				Forecast newFC = 
					weatherApiParser.parseWeatherapiString(forecastJSON, TOMORROWS_DATE);
				this.forecastRepo.save(newFC);
				resultList.add(newFC);
			}
		}
		
		return resultList;
	}
	
	
	
	public List<Forecast> retrieveForecast(String location) {
		
		List<Forecast> resultList = new ArrayList<>();
		
		ForecastID fcIDToday = new ForecastID(location, CURRENT_DATE);
		ForecastID fcIDTomorrow = new ForecastID(location, TOMORROWS_DATE);
		
		Optional<Forecast> todayFC = forecastRepo.findById(fcIDToday);
		Optional<Forecast> tomorrowFC = forecastRepo.findById(fcIDTomorrow);
		
		if(todayFC.isEmpty() && tomorrowFC.isEmpty()) {
			throw new NullPointerException(String.format(NA_ERROR_MSG, location));
		}
		
		if(todayFC.isPresent()) {
			resultList.add(todayFC.get());
		}
		
		if(tomorrowFC.isPresent()) {
			resultList.add(tomorrowFC.get());
		}
		
		return resultList;
	}
	
	public List<Forecast> retrieveAllForecasts() {
		
		List<Forecast> fcList = forecastRepo.getUpcomingForecasts(CURRENT_DATE, TOMORROWS_DATE);
		
		Collections.sort(fcList);
		
		return fcList;
	}
	
	private String getForecast(String location) throws IOException {
		
		URL url = new URL(String.format(ENDPOINT, KEY, location));
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		
		huc.setRequestMethod("GET");
		
		StringBuilder sb = new StringBuilder();
		
		if(huc.getResponseCode()<300) {
			
			try(BufferedReader bf = new BufferedReader(new InputStreamReader(huc.getInputStream()))){
				
				String result = "";
			
				while((result = bf.readLine())!=null) {
					sb.append(result);
				}
			}
		}

		huc.disconnect();
		
		return sb.toString();
	}
}
