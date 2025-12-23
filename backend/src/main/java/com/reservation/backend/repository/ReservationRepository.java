package com.reservation.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservation.common.model.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {


}
