package com.reservation.backend.controller;


import com.reservation.backend.service.ReservationService;
import com.reservation.common.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins="*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService rs){
        this.reservationService = rs;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation newReservation){
        // The @RequestBody annotation tells Spring to take the incoming JSON
        // and map it directly into your Reservation Java object.
        try{
            Reservation savedReservation = reservationService.saveReservation(newReservation);
            return ResponseEntity.ok(savedReservation);

        }catch(IllegalArgumentException e){
            //bad
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            //InternalServerError => 500 status
            return ResponseEntity.internalServerError().body("An error ocurred in our end: " +e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return ResponseEntity.ok(reservationService.listReservations());
    }
}
