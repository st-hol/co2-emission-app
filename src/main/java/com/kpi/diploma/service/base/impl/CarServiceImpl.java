package com.kpi.diploma.service.base.impl;

import com.google.common.collect.Lists;
import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateCarDto;
import com.kpi.diploma.repository.CarRepository;
import com.kpi.diploma.service.base.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return Lists.newArrayList(carRepository.findAll());
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car save(Car c) {
        return carRepository.save(c);
    }

    @Override
    public Page<Car> findAllByUserPageable(User user, PageRequest of) {
        return carRepository.findAllByUsers(user, of);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void createNewCar(CreateCarDto dto) {
        Car car = new Car();
        car.setName(dto.getName());
        car.setCylinders(dto.getCylinders());
        car.setEngineSize(dto.getEngineSize());
        car.setYearManufactured(dto.getYearManufactured());
        car.setFuelConsumptionComb(dto.getFuelConsumptionComb());
        carRepository.save(car);
    }
}
