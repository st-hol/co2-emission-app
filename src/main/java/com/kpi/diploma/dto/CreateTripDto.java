package com.kpi.diploma.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
public class CreateTripDto {
    private String name;

    private String from;

    private String to;

    private double distanceKm;

    //todo create awesome feature (or not)
    private boolean useExistingCar = true;

    @JsonUnwrapped
    private CarDto car;

    private boolean saveToHistory;
}
