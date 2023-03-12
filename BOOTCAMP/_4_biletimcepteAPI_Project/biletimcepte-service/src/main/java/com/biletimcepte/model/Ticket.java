package com.biletimcepte.model;

import com.biletimcepte.model.enums.GenderType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets",
        indexes = {
                @Index(name = "nationalIdentityNumber_index", columnList = "nationalIdentityNumber"),
                @Index(name = "passengerEmail_index", columnList = "passengerEmail"),
                @Index(name = "booking_index", columnList = "booking_id"),
                @Index(name = "voyage_index", columnList = "voyage_id")
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nationalIdentityNumber;
    private String passengerName;
    private String passengerSurname;
    private int passengerAge;
    @Column(nullable = false)
    private String passengerEmail;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    @ManyToOne
    @JoinColumn(name = "voyage_id", referencedColumnName = "id")
    private Voyage voyage;
    private double pricePerTicket;
    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime addDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationalIdentityNumber() {
        return nationalIdentityNumber;
    }

    public void setNationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSurname() {
        return passengerSurname;
    }

    public void setPassengerSurname(String passengerSurname) {
        this.passengerSurname = passengerSurname;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public double getPricePerTicket() {
        return pricePerTicket;
    }

    public void setPricePerTicket(double pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(int passengerAge) {
        this.passengerAge = passengerAge;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
    }

    public LocalDateTime getUpdDateTime() {
        return updDateTime;
    }

    public void setUpdDateTime(LocalDateTime updDateTime) {
        this.updDateTime = updDateTime;
    }
}
