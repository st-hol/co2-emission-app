package com.kpi.diploma.repository;


import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findAllByUsers(User user);

    Page<Trip> findAllByUsers(User user, Pageable pageable);

    int countTripsByUsers(User user);
}
