package com.reservation.common.dto;

import java.util.Date;
import java.util.List;
import com.reservation.common.model.Work;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateDTO {
    private String name;
    private String surname;

    @Email
    private String email;

    private List<Work> works;
    private Date dateDay;
    private Boolean assistanceConfirmation;
}