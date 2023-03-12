package com.biletimcepte.controller;

import com.biletimcepte.dto.request.TicketRequest;
import com.biletimcepte.dto.response.TicketResponse;
import com.biletimcepte.service.ITicketService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/tickets")
public class TicketController {
    private ITicketService iTicketService;

    @Autowired
    public TicketController(ITicketService iTicketService){
        setITicketService(iTicketService);
    }

    @GetMapping("/{id}")
    public TicketResponse getByID(@PathVariable int id){
        return getITicketService().getByTicketID(id);
    }

    @GetMapping("/user/{email}")
    public List<TicketResponse> getById(@PathVariable String eMail) {
        return getITicketService().getAllByPassengerEmail(eMail);
    }

    @PostMapping("/{voyageId}")
    public TicketResponse create(@PathVariable int voyageID, @RequestBody TicketRequest ticketRequest) {
        return getITicketService().create(voyageID, ticketRequest);
    }

    @PutMapping("/{ticketId}")
    public TicketResponse update(@PathVariable int ticketID, @RequestBody TicketRequest ticketRequest) {
            return getITicketService().update(ticketID, ticketRequest);
    }

    @DeleteMapping("/{ticketId}")
    public TicketResponse delete(@PathVariable int ticketID) {
        return getITicketService().delete(ticketID);
    }
}
