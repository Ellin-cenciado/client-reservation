package com.reservation.backend.service;

import java.util.List;

import com.reservation.common.dto.ReservationCreateDTO;
import com.reservation.common.model.Reservation;

public interface ReservationServiceImpl {
     List<ReservationCreateDTO> listReservations();
     ReservationCreateDTO findReservationById(Integer reservationId);
     ReservationCreateDTO saveReservation(ReservationCreateDTO reservation);
     void deleteReservationById(Integer id);
     void deleteReservation(ReservationCreateDTO reservation);

}

