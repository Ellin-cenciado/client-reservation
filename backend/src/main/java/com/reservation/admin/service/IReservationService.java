package com.reservation.admin.service;

import java.util.List;

import com.reservation.common.model.Reservation;

public interface IReservationService {
    public List<Reservation> listReservations();
    public Reservation findReservationById(Integer reservationId);
    public Reservation saveReservation(Reservation reservation);
    public void deleteReservationById(Integer id);
    public void deleteReservation(Reservation reservation);

}
/* 
    public List<Note> listNotes();
    public Note getNoteById(Integer noteId);
    public Note saveNote(Note note);
    public void deleteNote(Note note);
 */

