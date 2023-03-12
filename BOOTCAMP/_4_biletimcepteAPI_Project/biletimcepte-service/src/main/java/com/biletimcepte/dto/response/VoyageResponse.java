package com.biletimcepte.dto.response;

import com.biletimcepte.model.enums.TravelType;
import com.biletimcepte.model.enums.VoyageStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoyageResponse {
    private String fromCity;
    private String toCity;
    private TravelType travelType;
    private int availableSeats;
    private double pricePerTicket;
    private VoyageStatus voyageStatus;
    private double totalTicketPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime voyageDateTime;
}
