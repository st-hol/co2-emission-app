package com.kpi.diploma.service.co2;


import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateCarDto;
import com.kpi.diploma.dto.CreateTripDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CO2AmountService {

    double calculateCO2ForTrip(CreateTripDto dto);
}
