package com.kpi.diploma.service.base;


import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.domain.user.role.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Role save(Role role);

    List<Role> findAllByUser(User user);

    Role obtainRoleByName(String nameRole);
}
