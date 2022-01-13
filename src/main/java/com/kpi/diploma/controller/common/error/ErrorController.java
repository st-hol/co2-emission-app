package com.kpi.diploma.controller.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @GetMapping(value = "/403")
    public String personalCabinet() {
        log.info("Attempt of unlawful action was prevented. Access denied ");
        return "common/error/403";
    }

}
