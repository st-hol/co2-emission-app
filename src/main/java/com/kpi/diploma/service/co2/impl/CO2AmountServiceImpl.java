package com.kpi.diploma.service.co2.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.type.FuelType;
import com.kpi.diploma.domain.type.typed.TypeEnum;
import com.kpi.diploma.dto.DriveTripDto;
import com.kpi.diploma.feign.CO2EmissionsClient;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.co2.CO2AmountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CO2AmountServiceImpl implements CO2AmountService {
	private static final Map<FuelType, Double> ftFloatingCoefficientCorrelationMap = ImmutableMap.of(
			FuelType.DIESEL, 1.171,
			FuelType.GAS, 0.967,
			FuelType.PETROL, 1.0,
			FuelType.HYBRID, 0.735,
			FuelType.UNDEFINED, 1.0
	);

	@Autowired
	private CO2EmissionsClient co2EmissionsClient;
	@Autowired
	private CarService carService;

	@Override
	public double calculateCO2ForTrip(DriveTripDto dto) {
		CO2EmissionsClient.PredictCO2Request request;
		if (dto.isTestTrip()) {
			request = new CO2EmissionsClient.PredictCO2Request(dto.getEngineSize(), dto.getCylinders(), dto.getFuelConsumptionComb());
		} else {
			Car car = carService.findById(dto.getCarId());
			request = new CO2EmissionsClient.PredictCO2Request(car.getEngineSize(), car.getCylinders(), car.getFuelConsumptionComb());
		}
		log.info("Request co2 from feign with req {}", request);
		double emission = co2EmissionsClient.co2Emissions(request).getCo2();
		log.info("co2 emissions of {} is {} g/km", dto, emission);
		return calculateByFormula(emission, dto);
	}

	/**
	 * Calculate value.
	 *
	 * @param emission     g/km
	 * @param driveTripDto dto with trip info
	 * @return value of emissions per trip
	 */
	private double calculateByFormula(double emission, DriveTripDto driveTripDto) {
		FuelType fuelType;
		if (!driveTripDto.isTestTrip()) {
			Car car = carService.findById(driveTripDto.getCarId());
			fuelType = car.getFuelType();
		} else {
			fuelType = TypeEnum.findOptionalEnumValue(FuelType.class, driveTripDto.getFuelType()).orElse(FuelType.UNDEFINED);
		}
		final double emissionsKG = emission / 1000.0;
		return ftFloatingCoefficientCorrelationMap.get(fuelType) * emissionsKG * driveTripDto.getDistanceKm();
	}
}
