package com.kpi.diploma.dto;

import com.kpi.diploma.domain.type.EcoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CarDto {
    private Long id;

    private EcoType ecoType;

    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private double co2emissions;

    private String about;

}
