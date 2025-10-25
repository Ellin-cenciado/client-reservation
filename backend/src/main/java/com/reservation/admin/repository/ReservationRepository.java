package com.reservation.admin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.common.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    Reservation findReservationByUUID(UUID uuid);

}
