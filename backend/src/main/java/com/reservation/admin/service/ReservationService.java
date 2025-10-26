package com.reservation.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.common.model.Reservation;
import com.reservation.admin.repository.ReservationRepository;

@Service
public class ReservationService implements IReservationService{

    @Autowired
    private ReservationRepository ReservationRepository;

    @Override
    public List<Reservation> listReservations() {
        return ReservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Integer ReservationId) {
        return ReservationRepository.findById(ReservationId).orElse(null);
    }
    

    @Override
    public Reservation saveReservation(Reservation reservation) {
    return ReservationRepository.save(reservation);
}

    @Override
    public void deleteReservation(Reservation Reservation) {
        ReservationRepository.delete(Reservation);
    }
    
    @Override
    public void deleteReservationById(Integer id){
        ReservationRepository.deleteById(id);
    }
    
}
