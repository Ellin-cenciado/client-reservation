package com.reservation.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.reservation.common.model"})
public class ReservationBackend {

    public static void main(String[] args) {
        SpringApplication.run(ReservationBackend.class,args);
    }
}
