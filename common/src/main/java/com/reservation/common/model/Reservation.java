package com.reservation.common.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.reservation.common.converter.WorkListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Table(name = "clients")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "works")
@EqualsAndHashCode(exclude = "works")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid;
    private String name;
    private String surname;

    @Column(name = "workamount")
    private Integer worksAmount;

    @Convert(converter = WorkListConverter.class)
    @Column(name = "works")
    private List<Work> works;       // Uses the standalone Work enum

    @Column(name = "timestamp")
    private Date creationDate;

    @Column(name = "dateday")
    private Date dateDay;

    @Email
    private String email;

    @Column(name = "assistanceconfirmation")
    private Boolean assistanceConfirmation;
}