package com.kpi.diploma.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.repository.TripRepository;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.co2.CO2AmountService;

@Service
public class TripServiceImpl implements TripService {
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
    public void createNewTrip(CreateTripDto dto, double calculatedCO2ForTrip) {
        Trip trip = new Trip();
        trip.setCar(carService.findById(dto.getCar().getId()));
        trip.setName(dto.getName());
        trip.setFromCity(dto.getFrom());
        trip.setToCity(dto.getTo());
        trip.setDistanceKm(dto.getDistanceKm());
        trip.setCo2amount(calculatedCO2ForTrip);

        repository.save(trip);
    }
}