package com.biletimcepte.service;

import com.biletimcepte.dto.request.TicketRequest;
import com.biletimcepte.dto.response.TicketResponse;

import java.util.List;

public interface ITicketService {
    TicketResponse create(int voyageID, TicketRequest ticketRequest);
    TicketResponse update(int id, TicketRequest ticketRequest);
    TicketResponse getByTicketID(int id);
    TicketResponse delete(int id);
    List<TicketResponse> getAllByPassengerEmail(String eMail);
}
