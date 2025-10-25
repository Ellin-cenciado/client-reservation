package com.reservation.common.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "reservations_table")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Reservation {
    private enum work {
    TATTOO,
    PIERCING,
    REVISION,
    CHANGE,
    OTHER
}
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private Integer works_amount;
    private Enum<work>[] works;
    private Date creation_date;
    private Date date_day;
    private String name;
    private String surname;
    private Email email;
    private Boolean assistance_confirmation;
}
