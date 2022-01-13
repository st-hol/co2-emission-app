package com.kpi.diploma.repository.user;


import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.domain.user.role.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAllByUsers(User user);

    Role findByName(String nameRole);
}
