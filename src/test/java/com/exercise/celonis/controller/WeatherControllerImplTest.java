package com.exercise.celonis.controller;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.exercise.celonis.model.Forecast;
import com.exercise.celonis.service.WeatherServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(WeatherControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class WeatherControllerImplTest {
	
	final LocalDate TEST_DATE1 = LocalDate.of(2024, 12, 4);
	final LocalDate TEST_DATE2 = LocalDate.of(2024, 12, 5);
	final String MOCK_CITY = "Tokyo";
	final String MOCK_CONDITION = "Peace, Happiness, Sunshine";
	
	@MockBean
	WeatherServiceImpl weatherService;
    @Autowired
    private MockMvc mockMvc;
    
    final Forecast MOCKFORECAST1 = new Forecast(MOCK_CITY,TEST_DATE1,13.0, 15.0, 16.0, 7, MOCK_CONDITION);
	final Forecast  MOCKFORECAST2 = new Forecast(MOCK_CITY,TEST_DATE2,13.0, 15.0, 16.0, 7, MOCK_CONDITION);
	final List<Forecast> MOCKLIST = List.of(MOCKFORECAST1, MOCKFORECAST2);

	@Test
	void testRetrieveForecast() throws Exception  {
		
		Mockito.when(this.weatherService.retrieveForecast(MOCK_CITY)).thenReturn(MOCKLIST);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/weather").param("location", MOCK_CITY))
			.andExpect(status().isOk());
	}

	@Test
	void testRetrieveAllForecasts()  throws Exception {
		
		Mockito.when(this.weatherService.retrieveAllForecasts()).thenReturn(MOCKLIST);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/weather"))
			.andExpect(status().isOk());
	}

	/*@Test
	void testPersistForeCast() throws Exception {
		
		Mockito.when(this.weatherService.persistForeCast(MOCK_CITY)).thenReturn(MOCKLIST);
	
		mockMvc.perform(MockMvcRequestBuilders.post("/weather").param("location", MOCK_CITY))
			.andExpect(status().isOk());
	}*/
}
