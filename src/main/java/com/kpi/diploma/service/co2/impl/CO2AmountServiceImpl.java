package com.kpi.diploma.service.co2.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.feign.CO2EmissionsClient;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.co2.CO2AmountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CO2AmountServiceImpl implements CO2AmountService {

    @Autowired
    private CO2EmissionsClient co2EmissionsClient;
    @Autowired
    private CarService carService;

    @Override
    public double calculateCO2ForTrip(CreateTripDto dto) {
        CO2EmissionsClient.PredictCO2Request request;
        Car car = carService.findById(dto.getCarId());
        request = new CO2EmissionsClient.PredictCO2Request(car.getEngineSize(), car.getCylinders(), car.getFuelConsumptionComb());
        log.info("Request co2 from feign with req {}", request);
        double emission = co2EmissionsClient.co2Emissions(request).getCo2();
        log.info("co2 emissions of {} is {} g/km", car, emission);
        return calculateByFormula(emission, dto.getDistanceKm());
    }

    /**
     * Calculate value.
     * @param emission g/km
     * @param distanceKm km
     * @return value of emissions per trip
     */
    private double calculateByFormula(double emission, double distanceKm) {
        return emission * distanceKm;
    }
}
