package com.reservation.admin;

import com.reservation.admin.service.IReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.reservation.common.model")
public class ReservationApplication implements CommandLineRunner {

    @Autowired
    private IReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationApplication.class);

    public static void main(String[] args) {

        logger.info("Initializing Reservation Application");
        SpringApplication.run(ReservationApplication.class,args);
        logger.info("Reservation Application started successfully");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLineRunner executed");
        notesApp();
    }
    public void notesApp(){
        System.out.println("Welcome to Reservation Application");
        logger.info("Total reservations: " + reservationService.listReservations().size());
        logger.info("Reservations= \n" + reservationService.listReservations());

        while(true){
            // Infinite loop to keep the application running
        }

    }
}