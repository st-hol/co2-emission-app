package com.kpi.diploma.service.user;


import com.kpi.diploma.domain.user.role.Role;

import java.util.Set;


public interface SecurityService {
    String findLoggedInUsername();

    void autoLoginAfterReg(String username, String password);

    Set<Role> getLoggedUserRoles();
}
