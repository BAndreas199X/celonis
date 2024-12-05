package com.exercise.celonis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.celonis.model.Forecast;
import com.exercise.celonis.service.WeatherService;

@RestController("weather")
public class WeatherControllerImpl implements WeatherController {
	
	private WeatherService weatherService;
	
	public WeatherControllerImpl(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	@PostMapping("/weather/{location}")
	public ResponseEntity<List<Forecast>> persistForeCast(@PathVariable("location") String location) throws IOException{
		
		return new ResponseEntity<>(weatherService.persistForeCast(location), HttpStatus.OK);
	}
	
	@GetMapping("/weather/{location}")
	public ResponseEntity<List<Forecast>> retrieveForecast(@PathVariable("location") String location){

		return new ResponseEntity<>(weatherService.retrieveForecast(location), HttpStatus.OK);
	}
	
	@GetMapping("/weather")
	public ResponseEntity<List<Forecast>> retrieveAllForecasts(){

		return new ResponseEntity<>(weatherService.retrieveAllForecasts(), HttpStatus.OK);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOPointerException(IOException ex) {
	    
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
	    
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
	    
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
