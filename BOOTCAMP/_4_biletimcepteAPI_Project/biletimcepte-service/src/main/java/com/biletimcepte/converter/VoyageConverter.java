package com.biletimcepte.converter;

import com.biletimcepte.dto.request.VoyageRequest;
import com.biletimcepte.dto.response.VoyageResponse;
import com.biletimcepte.dto.response.VoyageTotalTicketsResponse;
import com.biletimcepte.model.Voyage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class VoyageConverter {
    public VoyageResponse convert(Voyage voyage) {
        VoyageResponse response = new VoyageResponse();

        response.setAvailableSeats(voyage.getAvailableSeats());
        response.setFromCity(voyage.getFromCity());
        response.setToCity(voyage.getToCity());
        response.setTravelType(voyage.getTravelType());
        response.setVoyageDateTime(voyage.getVoyageDateTime());
        response.setPricePerTicket(voyage.getPricePerTicket());
        response.setVoyageStatus(voyage.getVoyageStatus());
        response.setTotalTicketPrice(voyage.getTotalPrice());

        return response;
    }

    public Voyage convert(VoyageRequest voyageRequest, int seatNumbers){
        Voyage voyage = new Voyage();

        voyage.setTravelType(voyageRequest.getTravelType());
        voyage.setAvailableSeats(seatNumbers);
        voyage.setVoyageDateTime(voyageRequest.getVoyageDateTime());
        voyage.setAddDateTime(LocalDateTime.now());
        voyage.setFromCity(voyageRequest.getFromCity());
        voyage.setToCity(voyageRequest.getToCity());
        voyage.setPricePerTicket(voyageRequest.getPricePerTicket());

        return voyage;
    }

    public VoyageTotalTicketsResponse convert(int id, int totalSoldTickets){
        VoyageTotalTicketsResponse voyageTotalTicketsResponse = new VoyageTotalTicketsResponse();

        voyageTotalTicketsResponse.setVoyageID(id);
        voyageTotalTicketsResponse.setTotalSoldTickets(totalSoldTickets);

        return voyageTotalTicketsResponse;
    }

    public List<VoyageResponse> convert(List<Voyage> voyageList) {
        List<VoyageResponse> voyageResponse = new ArrayList<>();

        for (Voyage voyage : voyageList)
            voyageResponse.add(convert(voyage));

        return voyageList.stream().map(this::convert).toList();
    }
}
