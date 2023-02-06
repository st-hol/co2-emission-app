package com.kpi.diploma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <<BACKLOG>>
 * +landing
 * +singIn/signUp
 * +userCabinet:
 *     +garage
 *         +display all cars
 *         -make some limit where car is Green or Red by CO2
 *     +newTrip*with my car (here use existing car or enter new (with ability to save it to garage)): input -> city1,city2(or distance), car
 *     params; output -> co2 in kg
 *         +save car
 *         +save trip history
 *     +display all trips
 *     -update user's co2 amount for year
 *     +newTrip*just calc - do not save
 * +create 1year job for clearing year amount co2
 * -graphics func
 * +display yearly co2
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
