package com.kpi.diploma.service.base;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.GarageCarDto;

public interface CarService {
	List<Car> findAll();

	Car findById(Long id);

	Car save(Car c);

	Page<Car> findAllByUserPageable(User user, PageRequest of);

	void createNewCar(GarageCarDto dto, MultipartFile file) throws IOException;

	void delete(Long id);

	List<Car> findAllByUser(User user);

	void updateCar(GarageCarDto dto, MultipartFile file) throws IOException;

}
