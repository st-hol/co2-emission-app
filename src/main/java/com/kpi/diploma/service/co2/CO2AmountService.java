package com.kpi.diploma.service.co2;

import com.kpi.diploma.dto.CreateTripDto;

public interface CO2AmountService {

    double calculateCO2ForTrip(CreateTripDto dto);
}
