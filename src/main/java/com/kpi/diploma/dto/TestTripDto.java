package com.kpi.diploma.dto;

import lombok.Data;

@Data
public class TestTripDto {
    private String name;

    private String from;

    private String to;

    private double distanceKm;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private String fuelType;
}
