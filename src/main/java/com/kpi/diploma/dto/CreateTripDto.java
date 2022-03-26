package com.kpi.diploma.dto;

import lombok.Data;

@Data
public class CreateTripDto {
    private String name;

    private String from;

    private String to;

    private double distanceKm;

    //todo create awesome feature (or not)
    private boolean useExistingCar = true;

    private Long carId;

    private String about;

    private boolean saveToHistory;
}
