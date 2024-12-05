package com.exercise.celonis.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.exercise.celonis.model.Forecast;

@DataJpaTest
class ForecastRepositoryTest {
	
	final LocalDate TEST_DATE1 = LocalDate.of(2024, 12, 4);
	final LocalDate TEST_DATE2 = LocalDate.of(2024, 12, 5);
	final String MOCK_CITY = "Madrid";
	final String MOCK_CONDITION = "Peace, Happiness, Sunshine";
	
	final Forecast MOCKFORECAST1 = new Forecast(MOCK_CITY,TEST_DATE1,13.0, 15.0, 16.0, 7, MOCK_CONDITION);
	final Forecast  MOCKFORECAST2 = new Forecast(MOCK_CITY,TEST_DATE2,13.0, 15.0, 16.0, 7, MOCK_CONDITION);
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
    private ForecastRepository fcRepo;
	
	@BeforeEach
	void setUpBeforeClass() {
		
	    entityManager.persist(MOCKFORECAST1);
	    entityManager.persist(MOCKFORECAST2);
	}
	
	@Test
	void test() {
		
		List<Forecast> resultList = fcRepo.getUpcomingForecasts(TEST_DATE2, TEST_DATE1);
		
		assertFalse(resultList==null);
		assertEquals(2,resultList.size());

	}

}
