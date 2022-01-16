package com.kpi.diploma.repository;


import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAllByUsers(User user);

    Page<Car> findAllByUsers(User user, Pageable pageable);

    int countCarsByUsers(User user);
}
