package com.reservation.backend.service;

import com.reservation.common.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReservationConfirmation(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Who it goes to
        message.setTo(reservation.getEmail());

        // Spring requires a 'from' address
        message.setFrom("${MAIL_USERNAME}");

        message.setSubject("Reservation Confirmed: " + reservation.getName());

        // Formatting the email body using your existing object structure
        String text = String.format("""
                Hello %s %s,
                
                Your reservation has been successfully booked!
                
                Date: %tF at %tT
                Services requested: %s
                
                Thank you!
                """,
                reservation.getName(),
                reservation.getSurname(),
                reservation.getDateDay(),
                reservation.getDateDay(),
                reservation.getWorks().toString());

        message.setText(text);

        try {
            mailSender.send(message);
            System.out.println("Confirmation email sent to Mailtrap for: " + reservation.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}