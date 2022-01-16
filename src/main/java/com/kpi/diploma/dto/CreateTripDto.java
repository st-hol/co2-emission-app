package com.kpi.diploma.dto;

import com.kpi.diploma.domain.Car;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class CreateTripDto {
    private String name;

    private String from;

    private String to;

    private double distanceKm;

    private Long carId;

    private boolean saveToHistory;
}
