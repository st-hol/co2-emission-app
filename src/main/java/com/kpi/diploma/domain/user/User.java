package com.kpi.diploma.domain.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.apache.commons.lang3.RandomStringUtils;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.user.role.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"roles", "cars", "trips"})
@EqualsAndHashCode
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String contactNumber;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany
    @JoinTable(name = "users_has_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private boolean enabled;
    private String name;
    private String accountNumber;
    private BigDecimal co2YearAmount;

    /**
     * users have cars
     */
    @ManyToMany
    @JoinTable(name = "garage",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<Car> cars = new ArrayList<>();

    /**
     * users have trips
     */
    @ManyToMany
    @JoinTable(name = "journey",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Trip> trips = new ArrayList<>();

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
