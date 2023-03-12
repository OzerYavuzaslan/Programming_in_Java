package com.biletimcepte.model;

import com.biletimcepte.model.enums.PaymentStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings",
        indexes = {
                @Index(name = "passengerUser_index", columnList = "passenger_id"),
                @Index(name = "addDate_index", columnList = "addDate"),
                @Index(name = "passengerUser_index", columnList = "passenger_id")
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> ticketList;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime addDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updDate;
    private double bookingTotalPrice;
    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private User passengerUser;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private boolean isActive = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public double getBookingTotalPrice() {
        return bookingTotalPrice;
    }

    public void setBookingTotalPrice(double bookingTotalPrice) {
        this.bookingTotalPrice = bookingTotalPrice;
    }

    public User getPassengerUser() {
        return passengerUser;
    }

    public void setPassengerUser(User passengerUser) {
        this.passengerUser = passengerUser;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
