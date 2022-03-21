package com.kpi.diploma.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.kpi.diploma.domain.type.EcoType;
import com.kpi.diploma.domain.type.FuelType;
import com.kpi.diploma.domain.user.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "users")
@EqualsAndHashCode
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EcoType ecoType = EcoType.UNDEFINED;

    private String name;

    private int yearManufactured;

    private double engineSize;

    private int cylinders;

    private double fuelConsumptionComb;

    private FuelType fuelType = FuelType.UNDEFINED;

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
