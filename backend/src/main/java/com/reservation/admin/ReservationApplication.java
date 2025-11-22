package com.reservation.admin;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.reservation.admin.gui.Form;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
@EntityScan(basePackages = {"com.reservation.common.model"})
public class ReservationApplication {

    public static void main(String[] args) {
        System.out.println("Application starting: " + System.currentTimeMillis());
        FlatDarculaLaf.setup();

        ConfigurableApplicationContext springContext = new SpringApplicationBuilder(ReservationApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
        System.out.println("Spring context ready: " + System.currentTimeMillis());
        SwingUtilities.invokeLater(() -> {
            Form reservationForm = springContext.getBean(Form.class);
            reservationForm.setVisible(true);
        });
    }


}