package com.kpi.diploma.service.co2;

import java.util.Map;

import com.kpi.diploma.domain.user.User;

public interface StatsService {

	Map<String, Double> calcCurrentMonthDailyExhaust(User user);

	Map<String, Double> calcEmissionsByMonths(User user);

	Map<String, Double> calcCarUsageFrequencyToPercents(User user);
}
