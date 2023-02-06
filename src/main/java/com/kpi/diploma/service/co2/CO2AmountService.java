package com.kpi.diploma.service.co2;

import com.kpi.diploma.dto.DriveTripDto;

public interface CO2AmountService {

    double calculateCO2ForTrip(DriveTripDto dto);
}
