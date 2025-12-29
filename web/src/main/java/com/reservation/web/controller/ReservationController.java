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
import com.reservation.common.dto.ReservationCreateDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllreservation() {
        return reservationService.listReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.findReservationById(id);
    }

    @PostMapping
    public Reservation addReservation(@RequestBody ReservationCreateDTO dto) {
        System.out.println("Received reservation: " + dto);
        return reservationService.createReservation(dto);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        reservation.setId(id);
    return reservationService.saveReservation(reservation); // Return the saved reservation
}

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation != null) {
            reservationService.deleteReservation(reservation);
        }
    }
}