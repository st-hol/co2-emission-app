package com.kpi.diploma.service.base;


import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateTripDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TripService {
    List<Trip> findAll();

    Trip findById(Long id);

    Trip save(Trip trip);

    Page<Trip> findAllByUserPageable(User user, PageRequest of);

    void createNewTrip(CreateTripDto dto, double calculatedCO2ForTrip);
}
