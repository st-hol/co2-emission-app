package com.kpi.diploma.service.co2.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.data.Percentage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.service.base.TripService;

@ExtendWith(MockitoExtension.class)
class TestStatsServiceImpl {

	@Mock
	private TripService tripService;
	@InjectMocks
	private StatsServiceImpl underTest;

	@Test
	void calcCurrentMonthDailyExhaust() {
		final LocalDate now = LocalDate.now();
		final int currentMonth = now.getMonth().getValue();
		final String currentMonthName = now.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		final User user = new User();
		when(tripService.findAllByUser(user)).thenReturn(Arrays.asList(
				prepareTrip(1L, 1.0, LocalDate.of(2000, currentMonth, 1)),
				prepareTrip(2L, 2.0, LocalDate.of(2000, currentMonth, 2)),
				prepareTrip(3L, 3.3, LocalDate.of(2000, currentMonth, 3)),
				prepareTrip(4L, 4.0, LocalDate.of(2000, currentMonth, 4)),
				prepareTrip(5L, 5.1, LocalDate.of(2000, currentMonth, 5)),
				prepareTrip(6L, 6.0, LocalDate.of(2000, currentMonth, 21)),
				prepareTrip(7L, 7.5, LocalDate.of(2000, currentMonth, 22)),
				prepareTrip(8L, 8.0, LocalDate.of(2000, currentMonth, 25)),
				prepareTrip(9L, 9.9, LocalDate.of(2000, currentMonth, 25)),
				prepareTrip(10L, 10.2, LocalDate.of(2000, currentMonth, 25)),
				//November 0
				prepareTrip(12L, 1100.55, LocalDate.of(2000, currentMonth + 1, 1)),
				prepareTrip(13L, 10002.1, LocalDate.of(2000, currentMonth + 1, 1)),
				prepareTrip(14L, 10003.0, LocalDate.of(2000, currentMonth + 1, 1)),
				prepareTrip(15L, 100004.141, LocalDate.of(2000, currentMonth + 1, 1))
		));
		final Map<String, Double> stringDoubleMap = underTest.calcCurrentMonthDailyExhaust(user);
		assertThat(stringDoubleMap.get(currentMonthName + " 1")).isCloseTo(1.0, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get(currentMonthName + " 2")).isCloseTo(2.0, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get(currentMonthName + " 25")).isCloseTo(28.1, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get(currentMonthName + " 10")).isCloseTo(0, Percentage.withPercentage(5));
	}

	@Test
	void calcEmissionsByMonths() {
		final User user = new User();
		when(tripService.findAllByUser(user)).thenReturn(Arrays.asList(
				prepareTrip(1L, 1.0, LocalDate.of(2000, 1, 1)),
				prepareTrip(2L, 2.0, LocalDate.of(2000, 2, 1)),
				prepareTrip(3L, 3.3, LocalDate.of(2000, 3, 1)),
				prepareTrip(4L, 4.0, LocalDate.of(2000, 4, 1)),
				prepareTrip(5L, 5.1, LocalDate.of(2000, 5, 1)),
				prepareTrip(6L, 6.0, LocalDate.of(2000, 6, 1)),
				prepareTrip(7L, 7.5, LocalDate.of(2000, 7, 1)),
				prepareTrip(8L, 8.0, LocalDate.of(2000, 8, 1)),
				prepareTrip(9L, 9.9, LocalDate.of(2000, 9, 1)),
				prepareTrip(10L, 10.1, LocalDate.of(2000, 10, 1)),
				//November 0
				prepareTrip(12L, 11.55, LocalDate.of(2000, 12, 1)),
				prepareTrip(13L, 12.1, LocalDate.of(2000, 1, 1)),
				prepareTrip(14L, 13.0, LocalDate.of(2000, 2, 1)),
				prepareTrip(15L, 14.141, LocalDate.of(2000, 3, 1))
		));
		final Map<String, Double> stringDoubleMap = underTest.calcEmissionsByMonths(user);
		assertThat(stringDoubleMap.get("December")).isCloseTo(11.55, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get("April")).isCloseTo(4.0, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get("January")).isCloseTo(13.1, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get("November")).isCloseTo(0, Percentage.withPercentage(5));
	}

	@Test
	void calcCarUsageFrequencyToPercents() {
		final User user = new User();
		when(tripService.findAllByUser(user)).thenReturn(Arrays.asList(
				prepareTrip(1L, prepareCar("Ford")),
				prepareTrip(2L, prepareCar("Ford")),
				prepareTrip(3L, prepareCar("Ford")),
				prepareTrip(4L, prepareCar("BMW")),
				prepareTrip(5L, prepareCar("AUDI")),
				prepareTrip(6L, prepareCar("VW")),
				prepareTrip(7L, prepareCar("Mercedes")),
				prepareTrip(8L, prepareCar("Toyota")),
				prepareTrip(9L, prepareCar("Kia")),
				prepareTrip(10L, prepareCar("Kia")),
				prepareTrip(11L, prepareCar("Mercedes")),
				prepareTrip(12L, prepareCar("Mercedes")),
				prepareTrip(13L, prepareCar("Mercedes"))
		));
		final Map<String, Double> stringDoubleMap = underTest.calcCarUsageFrequencyToPercents(user);
		assertThat(stringDoubleMap.get("Ford")).isCloseTo(23, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get("Mercedes")).isCloseTo(30, Percentage.withPercentage(5));
		assertThat(stringDoubleMap.get("BMW")).isCloseTo(7.7, Percentage.withPercentage(5));
	}

	@NotNull
	private Car prepareCar(String name) {
		final Car car = new Car();
		car.setName(name);
		return car;
	}

	@NotNull
	private static Trip prepareTrip(Long id, double kgCO2, LocalDate localDate) {
		final Trip trip = new Trip();
		trip.setId(id);
		trip.setCo2amount(kgCO2);
		trip.setDate(localDate);
		return trip;
	}

	@NotNull
	private static Trip prepareTrip(Long id, Car car) {
		final Trip trip = new Trip();
		trip.setId(id);
		trip.setCar(car);
		return trip;
	}
}