package com.reservation.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.common.model.Reservation;
import com.reservation.backend.service.ReservationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService ReservationService;

    @GetMapping
    public List<Reservation> getAllreservation() {
        return ReservationService.listReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return ReservationService.findReservationById(id);
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        System.out.println("Received reservation: " + reservation);
        return ReservationService.saveReservation(reservation);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        reservation.setId(id);
    return ReservationService.saveReservation(reservation); // Return the saved reservation
}

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        Reservation reservation = ReservationService.findReservationById(id);
        if (reservation != null) {
            ReservationService.deleteReservation(reservation);
        }
    }
}