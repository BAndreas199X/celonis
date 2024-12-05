package com.exercise.celonis.parsers;

import java.time.LocalDate;

import com.exercise.celonis.model.Forecast;

public interface WeatherApiParser {

	Forecast parseWeatherapiString(String resultString, LocalDate date);
}
