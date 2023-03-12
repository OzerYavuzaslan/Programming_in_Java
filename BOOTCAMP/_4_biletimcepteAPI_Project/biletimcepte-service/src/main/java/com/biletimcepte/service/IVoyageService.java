package com.biletimcepte.service;

import com.biletimcepte.dto.request.VoyageRequest;
import com.biletimcepte.dto.response.VoyageResponse;
import com.biletimcepte.dto.response.VoyageTotalTicketsResponse;
import com.biletimcepte.model.Voyage;

import java.util.List;

public interface IVoyageService {
    VoyageResponse createVoyage(VoyageRequest voyageRequest);
    VoyageResponse setVoyagePassive(int id);
    VoyageResponse deleteVoyage(int id);
    VoyageResponse getTotalTicketPriceFromVoyage(int id);
    VoyageResponse updateVoyage(int id, VoyageRequest voyageRequest);
    List<VoyageResponse> getVoyageList();
    VoyageTotalTicketsResponse getTotalSoldTickets(int id);
    List<VoyageResponse> findVoyageList(String startDateTime, String endDateTime, String travelType,
                                        String fromCity, String toCity);
    Voyage getVoyageById(int id);
}
