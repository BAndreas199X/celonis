package com.exercise.celonis.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exercise.celonis.model.Forecast;

@Service
public interface WeatherService {

	List<Forecast> persistForeCast(String location) throws IOException;
	
	List<Forecast> retrieveForecast(String location);
	
	List<Forecast> retrieveAllForecasts();
}
