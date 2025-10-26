package com.reservation.common.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "clients")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "works")
@EqualsAndHashCode(exclude = "works")

public class Reservation {
    public enum Work {
    TATTOO,
    PIERCING,
    REVISION,
    CHANGE,
    OTHER
}
    @Id
    
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    
    private UUID uuid;
    @Column(name = "workamount")
    private Integer worksAmount;

    @ElementCollection(targetClass = Work.class)
    @Enumerated(EnumType.STRING)
    private List<Work> works;
    
    @Column(name = "timestamp")
    private Date creationDate;

    @Column(name = "dateday")
    private Date dateDay;
    private String name;
    private String surname;

    @Email
    private String email;

    @Column(name = "assistanceconfirmation")
    private Boolean assistanceConfirmation;
}
