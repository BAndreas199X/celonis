package com.exercise.celonis.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@Entity
@IdClass(ForecastID.class)
public class Forecast implements Comparable<Forecast> {
	
	@Id
	private String location;
	@Id
	private LocalDate date;
	private double maxTemperate;
	private double minTemperate;
	private double precipitation;
	private int humidity;
	private String condition;
	
	@Override
	public int compareTo(Forecast o) {
		
		return this.location.compareTo(o.getLocation());
	}

}
