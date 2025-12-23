package com.reservation.common.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.reservation.common.converter.WorkListConverter;
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
        PIERCING("Piercing"),
        TATTOO("Tattoo"),
        REVISION("Revision"),
        CHANGE("Change"),
        OTHER("Other");

        private final String displayName;

        Work(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
    @Id
    
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    private String name;
    private String surname;

    @Column(name = "workamount")
    private Integer worksAmount;

    @Convert(converter = WorkListConverter.class)
    @Column(name = "works")
    private List<Work> works;
    
    @Column(name = "timestamp")
    private Date creationDate;

    @Column(name = "dateday")
    private Date dateDay;

    @Email
    private String email;

    @Column(name = "assistanceconfirmation")
    private Boolean assistanceConfirmation;

}
