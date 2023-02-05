package com.kpi.diploma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CO2CalculatedDto {
	private final String from;
	private final String to;
	private final String carName;
	private final double engineSize;
	private final double fuelConsumptionComb;
	private final double co2Amount;
}
