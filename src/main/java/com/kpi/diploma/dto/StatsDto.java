package com.kpi.diploma.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class StatsDto {
	//switch real and mocked data for nice presentation
	private final boolean simulationDemoModeEnabled;

	//LINE CHART: Oct 1 -> 1000 gram , N ...
	private final Map<String, Double> currentMonthDailyExhaust;
	private final int currentMonthDailyExhaustMinY; // gram per day
	private final int currentMonthDailyExhaustMaxY; // gram per day

	//BAR CHART: October - 2,5 kg, November - 4 kg , N ...
	private final Map<String, Double> emissionsByMonths;
	private final int emissionsByMonthsMinY; // kg per month
	private final int emissionsByMonthsMaxY; // kg per month
	private final Double emissionsByMonthsHighest;
	private final Double emissionsByMonthsMedium;
	private final Double emissionsByMonthsLowest;

	//PIE CHART: BMW - 90%, Ford - 5%, N - n% ...
	private final Map<String, Double> carUsageFrequencyToPercents;
}
