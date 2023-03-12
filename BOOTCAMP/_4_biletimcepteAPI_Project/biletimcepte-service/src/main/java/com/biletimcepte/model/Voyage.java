package com.biletimcepte.model;

import com.biletimcepte.model.enums.TravelType;
import com.biletimcepte.model.enums.VoyageStatus;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voyages", indexes = {
        @Index(name = "toCity_index", columnList = "toCity"),
        @Index(name = "fromCity_index", columnList = "fromCity"),
        @Index(name = "voyageTime_index", columnList = "voyageDateTime"),
        @Index(name = "voyage_status_index", columnList = "voyageStatus"),
        @Index(name = "composite_index1", columnList = "availableSeats, voyageDateTime, toCity, voyageStatus"),
        @Index(name = "composite_index2", columnList = "availableSeats, voyageDateTime, fromCity, voyageStatus"),
        @Index(name = "composite_index3", columnList = "fromCity, toCity, voyageDateTime, travelType")
    })
public class Voyage {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String fromCity;
    private String toCity;
    private int availableSeats;
    private double totalPrice = 0.0D;
    private double pricePerTicket;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime voyageDateTime;
    @Enumerated(EnumType.STRING)
    private TravelType travelType;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voyage", cascade = CascadeType.ALL)
    private List<Ticket> ticketList;

    @Enumerated(EnumType.STRING)
    private VoyageStatus voyageStatus = VoyageStatus.ACTIVE;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime addDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getVoyageDateTime() {
        return voyageDateTime;
    }

    public void setVoyageDateTime(LocalDateTime voyageDateTime) {
        this.voyageDateTime = voyageDateTime;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public VoyageStatus getVoyageStatus() {
        return voyageStatus;
    }

    public void setVoyageStatus(VoyageStatus voyageStatus) {
        this.voyageStatus = voyageStatus;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
    }

    public double getPricePerTicket() {
        return pricePerTicket;
    }

    public void setPricePerTicket(double pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
