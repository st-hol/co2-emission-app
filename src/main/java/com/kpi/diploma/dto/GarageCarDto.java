package com.kpi.diploma.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GarageCarDto {
    private long id;
    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private String fuelType;

    private String about;

}
