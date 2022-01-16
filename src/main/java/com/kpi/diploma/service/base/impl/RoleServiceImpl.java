package com.kpi.diploma.service.base.impl;

import com.google.common.collect.Lists;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.domain.user.role.Role;
import com.kpi.diploma.repository.RoleRepository;
import com.kpi.diploma.service.base.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return Lists.newArrayList(roleRepository.findAll());
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Autowired
    public void setComplaintRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findAllByUser(User user) {
        return roleRepository.findAllByUsers(user);
    }

    @Override
    public Role obtainRoleByName(String nameRole) {
        return roleRepository.findByName(nameRole);
    }
}
