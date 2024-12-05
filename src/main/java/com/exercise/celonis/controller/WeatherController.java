package com.exercise.celonis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.exercise.celonis.model.Forecast;

public interface WeatherController {
	
	ResponseEntity<List<Forecast>> persistForeCast(String location) throws IOException;
	
	ResponseEntity<List<Forecast>> retrieveForecast(String location);
	
	ResponseEntity<List<Forecast>> retrieveAllForecasts();
}
