package com.exercise.celonis.dao;

import com.exercise.celonis.model.Forecast;
import com.exercise.celonis.model.ForecastID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<Forecast, ForecastID>{

	@Query(value="SELECT * FROM FORECAST WHERE DATE IN (:todaysDate, :tomorrowsDate);", nativeQuery = true)
	List<Forecast> getUpcomingForecasts(@Param("todaysDate") LocalDate todaysDate, 
		@Param("tomorrowsDate") LocalDate tomorrowsDate);
}
