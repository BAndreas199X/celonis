package com.exercise.celonis.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames=true)
@EqualsAndHashCode
public class ForecastID implements Serializable {
	
	private String location;
	private LocalDate date;
}
