package com.kpi.diploma.service.co2.impl;

import static com.kpi.diploma.feign.CO2EmissionsClient.PredictCO2Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.feign.CO2EmissionsClient;
import com.kpi.diploma.service.base.CarService;

/**
 * This test is dedicated to my beloved Karina, also known as Pidor.
 */
@ExtendWith(MockitoExtension.class)
class TestCO2AmountServiceImpl {

	@Mock
	private CarService carService;
	@Mock
	private CO2EmissionsClient co2EmissionsClient;
	@InjectMocks
	private CO2AmountServiceImpl serviceUnderTest;

	@Test
	void calculateCO2ForTrip() {
		//given
		CreateTripDto inputData = new CreateTripDto();
		inputData.setDistanceKm(100);
		inputData.setFrom("Kyiv");
		inputData.setTo("Lviv");
		inputData.setCarId(1L);

		Car fordFocus2 = new Car();
		fordFocus2.setEngineSize(5.0);
		fordFocus2.setCylinders(8);
		fordFocus2.setFuelConsumptionComb(15.2);
		when(carService.findById(1L)).thenReturn(fordFocus2);
		when(co2EmissionsClient.co2Emissions(any())).thenReturn(new PredictCO2Response(10));

		//when
		double result = serviceUnderTest.calculateCO2ForTrip(inputData);

		//then
		assertTrue(result > 0);
		assertEquals(1000, result);
		verify(carService).findById(1L);
		verify(co2EmissionsClient).co2Emissions(any());
		verifyNoMoreInteractions(carService);
	}
}