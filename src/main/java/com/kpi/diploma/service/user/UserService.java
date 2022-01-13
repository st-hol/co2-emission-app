package com.kpi.diploma.service.user;


import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.user.UserDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User findByUsername(String username);

    void registerUser(UserDto user);

    User obtainCurrentPrincipleUser();

    void updateUserInfo(UserDto user);

    UserDto convertToUserDto(User user);
}
