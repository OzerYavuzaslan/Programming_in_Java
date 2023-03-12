package com.biletimcepte.converter;

import com.biletimcepte.dto.request.BookingRequest;
import com.biletimcepte.dto.request.TicketRequest;
import com.biletimcepte.dto.response.TicketResponse;
import com.biletimcepte.model.Ticket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketConverter {
    public TicketResponse convert(Ticket ticket){
        TicketResponse response = new TicketResponse();

        response.setGenderType(ticket.getGenderType());
        response.setFromCity(ticket.getVoyage().getFromCity());
        response.setPassengerName(ticket.getPassengerName());
        response.setTicketPrice(ticket.getPricePerTicket());
        response.setToCity(ticket.getVoyage().getToCity());
        response.setPassengerSurname(ticket.getPassengerSurname());
        response.setVoyageDateTime(ticket.getVoyage().getVoyageDateTime());
        response.setNationalIdentityNumber(ticket.getNationalIdentityNumber());

        return response;
    }

    public Ticket convert(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();

        ticket.setNationalIdentityNumber(ticketRequest.getNationalIdentityNumber());
        ticket.setPassengerAge(ticketRequest.getPassengerAge());
        ticket.setPassengerName(ticketRequest.getPassengerName());
        ticket.setPassengerSurname(ticketRequest.getPassengerSurname());
        ticket.setPassengerEmail(ticketRequest.getPassengerEmail());
        ticket.setGenderType(ticketRequest.getGenderType());
        ticket.setAddDateTime(LocalDateTime.now());

        return ticket;
    }

    public Ticket convert(TicketRequest ticketRequest, Ticket ticket) {
        ticket.setNationalIdentityNumber(ticketRequest.getNationalIdentityNumber());
        ticket.setPassengerAge(ticketRequest.getPassengerAge());
        ticket.setPassengerName(ticketRequest.getPassengerName());
        ticket.setPassengerSurname(ticketRequest.getPassengerSurname());
        ticket.setPassengerEmail(ticketRequest.getPassengerEmail());
        ticket.setGenderType(ticketRequest.getGenderType());
        ticket.setUpdDateTime(LocalDateTime.now());

        return ticket;
    }

    public List<TicketResponse> convert(List<Ticket> ticketList) {
        List<TicketResponse> ticketResponse = new ArrayList<>();

        for (Ticket ticket : ticketList)
            ticketResponse.add(convert(ticket));

        return ticketList.stream().map(this::convert).toList();
    }

    public List<Ticket> convert(BookingRequest bookingRequest){
        Ticket ticket = new Ticket();
        List<Ticket> ticketList = new ArrayList<>();

        for (int i = 0; i < bookingRequest.getBookingTicketList().size(); i++){
            ticket.setAddDateTime(LocalDateTime.now());
            ticket.setGenderType(bookingRequest.getBookingTicketList().get(i).getGenderType());
            ticket.setPassengerEmail(bookingRequest.getPassengerEmail());
            ticket.setPassengerName(bookingRequest.getBookingTicketList().get(i).getPassengerName());
            ticket.setPassengerSurname(bookingRequest.getBookingTicketList().get(i).getPassengerSurname());
            ticket.setPassengerAge(bookingRequest.getBookingTicketList().get(i).getPassengerAge());
            ticket.setNationalIdentityNumber(bookingRequest.getBookingTicketList().get(i).getNationalIdentityNumber());
            ticketList.add(ticket);
        }

        return ticketList;
    }
}
