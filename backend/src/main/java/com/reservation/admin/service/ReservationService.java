package com.reservation.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.common.model.Reservation;
import com.reservation.admin.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService implements IReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> listReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        // Force initialization while session is still open
        reservations.forEach(r -> r.getWorks().size());
        return reservations;
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
