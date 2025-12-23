package com.reservation.backend.service;

import java.util.List;

import com.reservation.common.model.Reservation;

public interface ReservationServiceImpl {
     List<Reservation> listReservations();
     Reservation findReservationById(Integer reservationId);
     Reservation saveReservation(Reservation reservation);
     void deleteReservationById(Integer id);
     void deleteReservation(Reservation reservation);

}

