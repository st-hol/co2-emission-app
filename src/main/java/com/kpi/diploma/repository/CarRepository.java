package com.kpi.diploma.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAllByUsersIn(Set<User> users);

    Page<Car> findAllByUsersIn(Set<User> users, Pageable pageable);

    int countCarsByUsers(User user);
}
