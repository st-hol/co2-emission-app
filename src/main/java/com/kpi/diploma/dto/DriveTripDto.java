package com.kpi.diploma.dto;

import lombok.Data;

@Data
public class DriveTripDto {
	private String name;
	private String from;
	private String to;
	private double distanceKm;
	private Long carId;
	private String about;
	private boolean saveToHistory;
	private boolean testTrip = false;
	private int yearManufactured;
	private double engineSize;
	private int cylinders;
	private double fuelConsumptionComb;
	private String fuelType;
}
