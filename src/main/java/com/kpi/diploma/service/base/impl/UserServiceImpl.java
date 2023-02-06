package com.kpi.diploma.service.base.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.UserDto;
import com.kpi.diploma.repository.UserRepository;
import com.kpi.diploma.service.base.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(UserDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User userToSave = new User();
        BeanUtils.copyProperties(user, userToSave);
        userRepository.save(userToSave);
        log.info("user registered");
    }

    @Override
    public User obtainCurrentPrincipleUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal.toString())) {
            return null;
        }
        UserDetails userDetails = (UserDetails) principal;
        return userRepository.findByEmail(userDetails.getUsername());
    }

    @Override
    public void updateUserInfo(UserDto user) {
        User userToUpdate = findById(user.getId());
        BeanUtils.copyProperties(user, userToUpdate, getNullPropertyNames(user));
        save(userToUpdate);
    }

    @Override
    public UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto, getNullPropertyNames(user));
        return userDto;
    }

    /**
     * return names of null-fields
     *
     * @param source
     * @return
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
