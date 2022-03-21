package com.kpi.diploma.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateCarDto;

public interface CarService {
    List<Car> findAll();

    Car findById(Long id);

    Car save(Car c);

    Page<Car> findAllByUserPageable(User user, PageRequest of);

    void createNewCar(CreateCarDto dto);

    void delete(Long id);

    List<Car> findAllByUser(User user);
}
