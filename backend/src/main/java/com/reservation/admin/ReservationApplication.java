package com.reservation.admin;

import com.reservation.admin.service.IReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationApplication implements CommandLineRunner {

    @Autowired
    private IReservationService noteService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationApplication.class);

    public static void main(String[] args) {

        logger.info("Initializing Notes Application");
        SpringApplication.run(ReservationApplication.class,args);
        logger.info("Notes Application started successfully");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLineRunner executed");
        notesApp();
    }
    public void notesApp(){
        System.out.println("Welcome to Notes Application");
        logger.info("Total notes: " + noteService.listReservations().size());
        logger.info("Notes= \n" + noteService.listReservations());

        while(true){
            // Infinite loop to keep the application running
        }

    }
}