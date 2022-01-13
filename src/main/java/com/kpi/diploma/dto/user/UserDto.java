package com.kpi.diploma.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private Integer accountNumber;
    private BigDecimal balance;
//    private Currency currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
}
