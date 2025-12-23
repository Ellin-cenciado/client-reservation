package com.reservation.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.common.model.Reservation;
import com.reservation.backend.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService implements ReservationServiceImpl {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Integer ReservationId) {
        return reservationRepository.findById(ReservationId).orElse(null);
    }
    

    @Override
    public Reservation saveReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
}

    @Override
    public void deleteReservation(Reservation Reservation) {
        reservationRepository.delete(Reservation);
    }
    
    @Override
    public void deleteReservationById(Integer id){
        reservationRepository.deleteById(id);
    }
    
}
