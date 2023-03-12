package com.biletimcepte.service;

import com.biletimcepte.converter.TicketConverter;
import com.biletimcepte.dto.request.TicketRequest;
import com.biletimcepte.dto.response.TicketResponse;
import com.biletimcepte.exception.UserNotFoundException;
import com.biletimcepte.exception.VoyageNotFoundException;
import com.biletimcepte.model.Ticket;
import com.biletimcepte.model.Voyage;
import com.biletimcepte.repository.ITicketRepository;
import com.biletimcepte.repository.IUserRepository;
import com.biletimcepte.repository.IVoyageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.biletimcepte.util.Constants.USER_NOT_FOUND;
import static com.biletimcepte.util.Constants.VOYAGE_NOT_FOUND;

@Data
@Service
public class TicketService implements ITicketService{
    private ITicketRepository iTicketRepository;
    private IVoyageRepository iVoyageRepository;
    private IUserRepository iUserRepository;
    private TicketConverter ticketConverter;

    @Autowired
    public TicketService(ITicketRepository iTicketRepository, IVoyageRepository iVoyageRepository,
                         IUserRepository iUserRepository, TicketConverter ticketConverter) {
        setITicketRepository(iTicketRepository);
        setIVoyageRepository(iVoyageRepository);
        setIUserRepository(iUserRepository);
        setTicketConverter(ticketConverter);
    }


    @Override
    public TicketResponse create(int voyageID, TicketRequest ticketRequest) {
        Voyage voyage = getIVoyageRepository().findById(voyageID).orElseThrow(() -> new VoyageNotFoundException(VOYAGE_NOT_FOUND));
        Ticket ticket = getTicketConverter().convert(ticketRequest);
        ticket.setVoyage(voyage);
        ticket.setPricePerTicket(voyage.getPricePerTicket());
        getITicketRepository().save(ticket);

        return getTicketConverter().convert(ticket);
    }

    @Override
    public TicketResponse update(int id, TicketRequest ticketRequest) {
        return getTicketConverter()
                .convert(getITicketRepository()
                        .save(getTicketConverter()
                                .convert(ticketRequest,
                                getITicketRepository()
                                        .findById(id)
                                        .orElseThrow(() ->
                                                new VoyageNotFoundException(VOYAGE_NOT_FOUND)))));
    }

    @Override
    public TicketResponse getByTicketID(int id) {
        return getTicketConverter()
                .convert(getITicketRepository()
                        .findById(id)
                        .orElseThrow(() ->
                                new VoyageNotFoundException(VOYAGE_NOT_FOUND)));
    }

    @Override
    public TicketResponse delete(int id) {
        Ticket ticket = getITicketRepository().findById(id).orElseThrow(() -> new VoyageNotFoundException(VOYAGE_NOT_FOUND));
        getITicketRepository().delete(ticket);
        return getTicketConverter().convert(ticket);
    }

    @Override
    public List<TicketResponse> getAllByPassengerEmail(String eMail) {
        return getTicketConverter()
                .convert(getIUserRepository()
                        .selectByEmail(eMail)
                        .orElseThrow(() ->
                                new UserNotFoundException(USER_NOT_FOUND))
                        .getBookingList()
                        .stream()
                        .flatMap(singleBooking -> singleBooking.getTicketList()
                                .stream())
                        .toList());
    }
}