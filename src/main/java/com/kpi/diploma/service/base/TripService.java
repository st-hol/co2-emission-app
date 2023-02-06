package com.kpi.diploma.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.DriveTripDto;

public interface TripService {
    List<Trip> findAll();

    Trip findById(Long id);

    Trip save(Trip trip);

    Page<Trip> findAllByUserPageable(User user, PageRequest of);

    List<Trip> findAllByUser(User user);

    void createNewTrip(DriveTripDto dto, User user, double calculatedCO2ForTrip);
}
