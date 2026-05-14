package com.reservation.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.backend.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.dto.ReservationCreateDTO;


@Service
public class ReservationService implements ReservationServiceImpl {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationCreateDTO> listReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Integer ReservationId) {
        return reservationRepository.findById(ReservationId).orElse(null);
    }
    public Reservation createReservation(ReservationCreateDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setName(dto.getName());
        reservation.setSurname(dto.getSurname());
        reservation.setEmail(dto.getEmail());
        reservation.setWorks(dto.getWorks());
        reservation.setDateDay(dto.getDateDay());
        reservation.setAssistanceConfirmation(dto.getAssistanceConfirmation());

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
}

    @Override
    public void deleteReservation(ReservationCreateDTO Reservation) {
        reservationRepository.delete(Reservation);
    }
    
    @Override
    public void deleteReservationById(Integer id){
        reservationRepository.deleteById(id);
    }
    
}
