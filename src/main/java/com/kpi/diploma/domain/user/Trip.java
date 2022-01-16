package com.kpi.diploma.domain.user;

import com.kpi.diploma.domain.Car;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * users have trips
     */
    @ManyToMany
    @JoinTable(name = "journey",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
}
