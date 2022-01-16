package com.kpi.diploma.domain;

import com.kpi.diploma.domain.type.EcoType;
import com.kpi.diploma.domain.user.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.domain.user.role.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EcoType ecoType;

    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private double co2emissions;

    /**
     * users have cars
     */
    @ManyToMany
    @JoinTable(name = "garage",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;
}
