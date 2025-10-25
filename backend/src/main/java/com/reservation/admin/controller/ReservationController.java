package com.reservation.admin.controller;

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
import com.reservation.admin.service.ReservationService;

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
    public Reservation addReservation(@RequestBody Reservation note) {
        System.out.println("Received note: " + note);
        return ReservationService.saveReservation(note);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Integer id, @RequestBody Reservation note) {
    note.setId(id);
    return ReservationService.saveReservation(note); // Return the saved note
}

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        Reservation note = ReservationService.findReservationById(id);
        if (note != null) {
            ReservationService.deleteReservation(note);
        }
    }
}