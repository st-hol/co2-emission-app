package com.kpi.diploma.domain.user;

import com.kpi.diploma.domain.user.role.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String email;
    protected String password;
    @Transient
    protected String passwordConfirm;
    @ManyToMany
    @JoinTable(name = "users_has_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles = new HashSet<>();
    protected boolean enabled;
    private String name;
    private String accountNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @PrePersist
    void preInsert() {
        if (this.roles.isEmpty()) {
            Role role = new Role();
            role.setId(1L);
            role.setName("USER");
            roles.add(role);
        }
        this.accountNumber = RandomStringUtils.randomAlphanumeric(10);
    }
}
