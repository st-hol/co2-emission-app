package com.kpi.diploma.service.co2.impl;

import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.service.co2.CO2AmountService;
import org.springframework.stereotype.Service;

@Service
public class CO2AmountServiceImpl implements CO2AmountService {
    @Override
    public double calculateCO2ForTrip(CreateTripDto dto) {
        return 0;//todo
    }
}
