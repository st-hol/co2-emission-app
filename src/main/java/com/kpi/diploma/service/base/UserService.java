package com.kpi.diploma.service.base;

import java.util.List;

import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.UserDto;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    void registerUser(UserDto user);

    User obtainCurrentPrincipleUser();

    void updateUserInfo(UserDto user);

    UserDto convertToUserDto(User user);

	void incrementYearlyCO2(User user, double calculatedCO2ForTrip);
}
