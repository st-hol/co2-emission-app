package com.kpi.diploma.dto;

import com.kpi.diploma.domain.type.EcoType;
import lombok.Data;

@Data
public class CreateCarDto {
    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;
}
