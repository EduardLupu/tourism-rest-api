package com.example.app;

import com.example.app.model.*;
import com.example.app.repository.CityRepository;
import com.example.app.repository.CountryRepository;
import com.example.app.repository.TouristRepository;
import com.example.app.repository.VisitRepository;
import com.example.app.service.CountryService;
import com.example.app.service.TouristService;
import com.example.app.service.VisitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppApplicationTests {
	@Mock
	private CountryRepository countryRepository;
	@Mock
	private CityRepository cityRepository;
	@InjectMocks
	private CountryService countryService;

	@Mock
	private TouristRepository touristRepository;
	@InjectMocks
	private TouristService touristService;

	@Mock
	private VisitRepository visitRepository;
	@InjectMocks
	private VisitService visitService;

	@Test
	void testOrderCountriesByAverageDaysSpent() {
			// Arrange
			Tourist tourist1 = new Tourist();
			tourist1.setTouristId(1L);
			tourist1.setTouristName("John");
			tourist1.setTouristDateOfBirth(LocalDate.of(1990, 1, 1));
			tourist1.setTouristGender("Male");
			Visit visit1 = new Visit();
			visit1.setTourist(tourist1);
			visit1.setCountry(new Country());
			visit1.setMoneySpent(100);
			visit1.setDaysSpent(5);
			tourist1.getVisits().add(visit1);

			Tourist tourist2 = new Tourist();
			tourist2.setTouristId(2L);
			tourist2.setTouristName("Jane");
			tourist2.setTouristDateOfBirth(LocalDate.of(1995, 1, 1));
			tourist2.setTouristGender("Female");
			Visit visit2 = new Visit();
			visit2.setTourist(tourist2);
			visit2.setCountry(new Country());
			visit2.setMoneySpent(200);
			visit2.setDaysSpent(3);
			tourist2.getVisits().add(visit2);

			when(touristRepository.orderTouristsByTheTotalMoneySpentInVisits()).thenReturn(Arrays.asList(
					new TouristStatisticsDTO(2L, "Jane", LocalDate.of(1995, 1, 1), "Female", 200L),
					new TouristStatisticsDTO(1L, "John", LocalDate.of(1990, 1, 1), "Male", 100L)
			));

			// Act
			List<TouristStatisticsDTO> result = touristService.getTouristsInDescOrderBasedOnTotalMoneySpent();

			// Assert
			Assertions.assertEquals(2, result.size());
			Assertions.assertEquals("Jane", result.get(0).name());
			Assertions.assertEquals(200, result.get(0).totalMoneySpentInVisits());
			Assertions.assertEquals("John", result.get(1).name());
			Assertions.assertEquals(100, result.get(1).totalMoneySpentInVisits());
	}

	@Test
	void testOrderTouristsByTheTotalMoneySpentInVisits() {
		// Arrange
		List<Country> countries = Arrays.asList(
				new Country("USA", 1000000, 328200000, "US"),
				new Country("Canada", 9985000, 37742154, "CA"),
				new Country("Mexico", 1964375, 126190788, "MX"));
		List<Visit> usaVisits = Arrays.asList(
				new Visit(new Tourist(), countries.get(0), 500, 10),
				new Visit(new Tourist(), countries.get(0), 800, 14),
				new Visit(new Tourist(), countries.get(0), 1200, 21)
		);
		List<Visit> canadaVisits = Arrays.asList(
				new Visit(new Tourist(), countries.get(1), 700, 7),
				new Visit(new Tourist(), countries.get(1), 1000, 14),
				new Visit(new Tourist(), countries.get(1), 600, 5)
		);
		List<Visit> mexicoVisits = Arrays.asList(
				new Visit(new Tourist(), countries.get(2), 300, 3),
				new Visit(new Tourist(), countries.get(2), 400, 5),
				new Visit(new Tourist(), countries.get(2), 500, 7)
		);
		countries.get(0).setVisits(usaVisits);
		countries.get(1).setVisits(canadaVisits);
		countries.get(2).setVisits(mexicoVisits);

		List<CountryStatisticsDTO> expected = Arrays.asList(
				new CountryStatisticsDTO(1L, "USA", 15.0),
				new CountryStatisticsDTO(2L, "Canada", 8.67),
				new CountryStatisticsDTO(3L, "Mexico", 5.0)
		);

		when(countryService.getCountriesAverageDaysSpent()).thenReturn(expected);

		// When
		List<CountryStatisticsDTO> result = countryService.getCountriesAverageDaysSpent();

		// Then
		Assertions.assertEquals(result, expected);
		Assertions.assertEquals("USA", result.get(0).name());
		Assertions.assertEquals(15.0, result.get(0).averageDaysSpent());
		Assertions.assertEquals("Canada", result.get(1).name());
		Assertions.assertEquals(8.67, result.get(1).averageDaysSpent());
		Assertions.assertEquals("Mexico", result.get(2).name());
		Assertions.assertEquals(5.0, result.get(2).averageDaysSpent());
	}
}
