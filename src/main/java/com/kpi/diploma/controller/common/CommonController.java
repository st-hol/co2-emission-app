package com.kpi.diploma.controller.common;

import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.domain.user.role.Role;
import com.kpi.diploma.service.base.RoleService;
import com.kpi.diploma.service.base.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping({"/", "/welcome"})
    public String la(Model model) {
        return "common/index";
    }

    @GetMapping({"/default-home"})
    public String defaultHome(Principal principal) {
        if (principal == null) {
            log.info("No user is authorized. Can't go to personal cabinet.");
            return "redirect:/";
        }

        User loggedInUser = userService.findByUsername(principal.getName());
        List<Role> userRoles = roleService.findAllByUser(loggedInUser);

        Role adminRole = roleService.obtainRoleByName("ADMIN");
        Role clientRole = roleService.obtainRoleByName("USER");

        if (userRoles.contains(adminRole)) {
            return "redirect:/admin/home";
        } else if (userRoles.contains(clientRole)) {
            return "redirect:/user/home";
        } else {
            return "redirect:/";
        }

    }
}
