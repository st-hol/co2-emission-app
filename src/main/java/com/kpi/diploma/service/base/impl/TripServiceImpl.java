package com.kpi.diploma.service.base.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.DriveTripDto;
import com.kpi.diploma.repository.TripRepository;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.base.UserService;
import com.kpi.diploma.service.co2.CO2AmountService;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private UserService userService;
    @Autowired
    private TripRepository repository;
    @Autowired
    private CarService carService;
    @Autowired
    private CO2AmountService co2AmountService;

    @Override
    public List<Trip> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Trip findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Trip save(Trip t) {
        return repository.save(t);
    }

    @Override
    public Page<Trip> findAllByUserPageable(User user, PageRequest of) {
        return repository.findAllByUsers(user, of);
    }

    @Override
    public List<Trip> findAllByUser(User user) {
        return repository.findAllByUsers(user);
    }

    @Override
    public void createNewTrip(DriveTripDto dto, User user, double calculatedCO2ForTrip) {
        Trip trip = new Trip();
        trip.setCar(carService.findById(dto.getCarId()));
        trip.setName(dto.getName());
        trip.setFromCity(dto.getFrom());
        trip.setToCity(dto.getTo());
        trip.setDistanceKm(dto.getDistanceKm());
        trip.setCo2amount(calculatedCO2ForTrip);
        trip.setUsers(Collections.singletonList(user));
        trip.setDate(LocalDate.now());
        trip.setAbout(dto.getAbout());
        repository.save(trip);

        userService.incrementYearlyCO2(user, calculatedCO2ForTrip);
    }
}
