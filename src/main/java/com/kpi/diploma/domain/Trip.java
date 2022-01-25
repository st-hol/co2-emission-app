package com.kpi.diploma.domain;

import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCity;

    private String toCity;

    private double distanceKm;

    private String name;

    private double co2amount;

    @ManyToOne
    private Car car;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * users have trips
     */
    @ManyToMany
    @JoinTable(name = "journey",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
}
