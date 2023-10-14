package com.kpi.diploma.service.co2.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.co2.StatsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {

	private static final Map<String, Double> INITIAL_EMISSIONS_BY_MONTH = new TreeMap<>();
	private static final Map<String, String> SHORT_TO_LONG_MONTH_NAME = new HashMap<>();

	static {
		INITIAL_EMISSIONS_BY_MONTH.put("January", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("February", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("March", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("April", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("May", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("June", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("July", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("August", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("September", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("October", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("November", 0.0);
		INITIAL_EMISSIONS_BY_MONTH.put("December", 0.0);

		SHORT_TO_LONG_MONTH_NAME.put("Jan", "January");
		SHORT_TO_LONG_MONTH_NAME.put("Feb", "February");
		SHORT_TO_LONG_MONTH_NAME.put("Mar", "March");
		SHORT_TO_LONG_MONTH_NAME.put("Apr", "April");
		SHORT_TO_LONG_MONTH_NAME.put("May", "May");
		SHORT_TO_LONG_MONTH_NAME.put("Jun", "June");
		SHORT_TO_LONG_MONTH_NAME.put("Jul", "July");
		SHORT_TO_LONG_MONTH_NAME.put("Aug", "August");
		SHORT_TO_LONG_MONTH_NAME.put("Sep", "September");
		SHORT_TO_LONG_MONTH_NAME.put("Oct", "October");
		SHORT_TO_LONG_MONTH_NAME.put("Nov", "November");
		SHORT_TO_LONG_MONTH_NAME.put("Dec", "December");
	}

	@Autowired
	private TripService tripService;

	@Override
	public Map<String, Double> calcCurrentMonthDailyExhaust(User user) {
		final LocalDate now = LocalDate.now();
		final Month currentMonth = now.getMonth();
		final String currentMonthName = now.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		final List<Trip> allTripsByUserCurrentMonth = tripService.findAllByUser(user).stream()
				.filter(trip -> currentMonth.equals(trip.getDate().getMonth()))
				.toList();
		final Map<String, Double> emissionsByMonths = allTripsByUserCurrentMonth.stream()
				.collect(Collectors.groupingBy(trip -> currentMonthName + " " + trip.getDate().getDayOfMonth(),
						Collectors.summingDouble(Trip::getCo2amount)));

		final Map<String, Double> initialEmissionsByDaysOfCurrentMonth = IntStream.range(1, currentMonth.length(now.isLeapYear()))
				.mapToObj(day -> currentMonthName + " " + day)
				.collect(Collectors.toMap(Function.identity(), x -> 0.0));
		return Stream.of(emissionsByMonths, initialEmissionsByDaysOfCurrentMonth)
				.flatMap(map -> map.entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey,
						() -> new TreeMap<>(Comparator.comparing(s -> Integer.valueOf(s.substring(s.indexOf(" ") + 1)))),
						Collectors.summingDouble(Map.Entry::getValue)));
	}

	@Override
	public Map<String, Double> calcEmissionsByMonths(User user) {
		final List<Trip> allTripsByUser = tripService.findAllByUser(user);
		final Map<String, Double> emissionsByMonths = allTripsByUser.stream()
				.collect(Collectors.groupingBy(trip -> trip.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
						Collectors.summingDouble(Trip::getCo2amount)));
		return Stream.of(emissionsByMonths, INITIAL_EMISSIONS_BY_MONTH)
				.flatMap(map -> map.entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey,
						() -> new TreeMap<>(Comparator.comparing(m -> Month.valueOf(m.toUpperCase()))),
						Collectors.summingDouble(Map.Entry::getValue)));
	}

	@Override
	public Map<String, Double> calcCarUsageFrequencyToPercents(User user) {
		final List<Trip> allTripsByUser = tripService.findAllByUser(user);

		final int nTotalTrips = allTripsByUser.size();
		return allTripsByUser.stream()
				.collect(Collectors.groupingBy(trip -> trip.getCar().getName(),
						Collectors.collectingAndThen(Collectors.counting(), a -> (double) a / nTotalTrips * 100)));
	}

	//fractions
	//		return allTripsByUser.stream()
	//				.collect(Collectors.teeing(
	//						Collectors.groupingBy(trip -> trip.getCar().getName(), Collectors.counting()),
	//						Collectors.counting(),
	//						StatsServiceImpl::scale));
	//	static Map<String, Double> scale(Map<String, Long> counts, long total) {
	//		return counts.entrySet().stream()
	//				.collect(Collectors.toMap(ent -> ent.getKey(), ((double) ent.getValue()) / total));
	//	}
}
