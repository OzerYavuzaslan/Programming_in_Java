package com.biletimcepte.controller;

import com.biletimcepte.dto.request.VoyageRequest;
import com.biletimcepte.dto.response.VoyageResponse;
import com.biletimcepte.dto.response.VoyageTotalTicketsResponse;
import com.biletimcepte.service.IVoyageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/voyages")
public class VoyageController {
    private IVoyageService iVoyageService;

    @Autowired
    public VoyageController(IVoyageService iVoyageService){
        setIVoyageService(iVoyageService);
    }

    @PostMapping
    public VoyageResponse addVoyage(@RequestBody VoyageRequest voyageRequest){
        return getIVoyageService().createVoyage(voyageRequest);
    }

    @PutMapping("/setpassive/{id}")
    public VoyageResponse setPassive(@PathVariable int id){
        return getIVoyageService().setVoyagePassive(id);
    }

    @DeleteMapping("/{id}")
    public VoyageResponse delete(@PathVariable int id){
        return getIVoyageService().deleteVoyage(id);
    }

    @GetMapping("/totalprice/{id}")
    public VoyageResponse getTotalPrice(@PathVariable int id) {
        return getIVoyageService().getTotalTicketPriceFromVoyage(id);
    }

    @PutMapping("/{id}")
    public VoyageResponse update(@PathVariable int id, @RequestBody VoyageRequest voyageRequest){
        return getIVoyageService().updateVoyage(id, voyageRequest);
    }

    @GetMapping
    public List<VoyageResponse> getVoyageList() {
        return getIVoyageService().getVoyageList();
    }

    @PostMapping("/totalsoldtickets/{id}")
    public VoyageTotalTicketsResponse totalSoldTickets(@PathVariable int id){
        return getIVoyageService().getTotalSoldTickets(id);
    }

    @PostMapping("/findvoyages/{startDateTime}/{endDateTime}/{travelType}/{fromCity}/{toCity}")
    public List<VoyageResponse> findVoyages(@RequestParam (required = false) String startDateTime,
                                            @RequestParam (required = false) String endDateTime,
                                            @RequestParam (required = false) String travelType,
                                            @RequestParam (required = false) String fromCity,
                                            @RequestParam (required = false) String toCity){
        return getIVoyageService().findVoyageList(startDateTime, endDateTime, travelType, fromCity, toCity);
    }
}
