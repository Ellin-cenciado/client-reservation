package com.reservation.common.dto;

import java.util.Date;
import java.util.List;

import com.reservation.common.model.Work;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationCreateDTO {

    private String name;
    private String surname;
    private String email;
    private List<Work> works;
    private Date dateDay;
    private Boolean assistanceConfirmation;

}
