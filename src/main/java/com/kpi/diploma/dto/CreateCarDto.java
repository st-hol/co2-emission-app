package com.kpi.diploma.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CreateCarDto {
    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private String fuelType;
}
