package com.biletimcepte.service;

import com.biletimcepte.converter.VoyageConverter;
import com.biletimcepte.dto.request.VoyageRequest;
import com.biletimcepte.dto.response.VoyageResponse;
import com.biletimcepte.dto.response.VoyageTotalTicketsResponse;
import com.biletimcepte.exception.VoyageNotFoundException;
import com.biletimcepte.model.Voyage;
import com.biletimcepte.model.enums.TravelType;
import com.biletimcepte.model.enums.VoyageStatus;
import com.biletimcepte.repository.IUserRepository;
import com.biletimcepte.repository.IVoyageRepository;
import com.biletimcepte.repository.VoyageRepositoryJDBC;
import com.biletimcepte.util.DateTimeConverter;
import com.biletimcepte.util.LoggerUtilization;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

import static com.biletimcepte.util.Constants.VOYAGE_NOT_FOUND;

@Data
@Service
public class VoyageService implements IVoyageService{
    private IVoyageRepository IVoyageRepository;
    private VoyageConverter voyageConverter;
    private IUserRepository IUserRepository;
    private VoyageRepositoryJDBC voyageRepositoryJDBC;

    @Autowired
    public VoyageService(IVoyageRepository IVoyageRepository, VoyageConverter voyageConverter, IUserRepository IUserRepository,
                         VoyageRepositoryJDBC voyageRepositoryJDBC){
        setIUserRepository(IUserRepository);
        setVoyageConverter(voyageConverter);
        setIVoyageRepository(IVoyageRepository);
        setVoyageRepositoryJDBC(voyageRepositoryJDBC);
    }

    @Override
    public VoyageResponse createVoyage(VoyageRequest voyageRequest) {
        LoggerUtilization.getLogger().log(Level.INFO,"VoyageService -> createVoyage: " +
                voyageRequest.getFromCity() + " -> " +
                voyageRequest.getToCity() + " -> " +
                voyageRequest.getVoyageDateTime());

        return getVoyageConverter()
                .convert(getIVoyageRepository()
                .save(getVoyageConverter()
                        .convert(voyageRequest,
                                setSeatNumbers(voyageRequest.getTravelType()))));
    }

    @Override
    public VoyageResponse setVoyagePassive(int id) {
        Voyage voyage = getVoyageById(id);

        voyage.setVoyageStatus(VoyageStatus.PASSIVE);
        voyage.setUpdateDateTime(LocalDateTime.now());
        getIVoyageRepository().save(voyage);

        LoggerUtilization.getLogger().log(Level.INFO,"VoyageService -> setVoyagePassive: " +
                voyage.getFromCity() + " -> " +
                voyage.getToCity() + " -> " +
                voyage.getVoyageDateTime());

        return getVoyageConverter().convert(voyage);
    }

    @Override
    public VoyageResponse deleteVoyage(int id) {
        Voyage voyage = getVoyageById(id);
        getIVoyageRepository().delete(voyage);

        LoggerUtilization.getLogger().log(Level.INFO,"VoyageService -> deleteVoyage: " +
                voyage.getFromCity() + " -> " +
                voyage.getToCity() + " -> " +
                voyage.getVoyageDateTime());

        return getVoyageConverter().convert(voyage);
    }

    @Override
    public VoyageResponse getTotalTicketPriceFromVoyage(int id) {
        Voyage voyage = getVoyageById(id);
        return getVoyageConverter().convert(voyage);
    }

    @Override
    public VoyageResponse updateVoyage(int id, VoyageRequest voyageRequest) {
        Voyage voyage = getVoyageById(id);

        voyage.setUpdateDateTime(LocalDateTime.now());
        voyage.setPricePerTicket(voyageRequest.getPricePerTicket());
        voyage.setToCity(voyageRequest.getToCity());
        voyage.setFromCity(voyageRequest.getFromCity());
        voyage.setVoyageDateTime(voyageRequest.getVoyageDateTime());
        getIVoyageRepository().save(voyage);

        LoggerUtilization.getLogger().log(Level.INFO,"VoyageService -> updateVoyage: " +
                voyage.getFromCity() + " -> " +
                voyage.getToCity() + " -> " +
                voyage.getVoyageDateTime());

        return getVoyageConverter().convert(voyage);
    }

    @Override
    public List<VoyageResponse> getVoyageList() {
        return getVoyageConverter().convert(getIVoyageRepository().findAll());
    }

    @Override
    public VoyageTotalTicketsResponse getTotalSoldTickets(int id) {
        Voyage voyage = getVoyageById(id);
        return getVoyageConverter().convert(id, voyage.getTicketList().size());
    }

    @Override
    public List<VoyageResponse> findVoyageList(String startDateTime, String endDateTime,
                                               String travelType, String fromCity, String toCity) {
        LocalDateTime startDateTimeLDT = null;
        LocalDateTime endDateTimeLDT = null;

        if (startDateTime != null) {
            startDateTimeLDT = DateTimeConverter.convert(startDateTime);

            if (endDateTime == null)
                endDateTimeLDT = startDateTimeLDT.plusHours(48);
        }

        if (endDateTime != null) {
            endDateTimeLDT = DateTimeConverter.convert(endDateTime);

            if (startDateTime == null)
                startDateTimeLDT = endDateTimeLDT.minusHours(48);
        }

        if (startDateTimeLDT == null  && travelType == null && fromCity == null && toCity == null)
            return getVoyageConverter()
                    .convert(getIVoyageRepository()
                            .selectVoyagesByParameters(0, LocalDateTime.now(),
                                                        LocalDateTime.now().plusHours(72),
                                                        VoyageStatus.ACTIVE.toString()));

        return getVoyageConverter().convert(getVoyageRepositoryJDBC().filterVoyages(0, startDateTimeLDT,
                endDateTimeLDT, travelType, fromCity, toCity));
    }

    @Override
    public Voyage getVoyageById(int id){
        return getIVoyageRepository().findById(id).orElseThrow(() -> new VoyageNotFoundException(id + " -->" + VOYAGE_NOT_FOUND));
    }

    private int setSeatNumbers(TravelType travelType){
        if (travelType.equals(TravelType.BUS))
            return 45;

        return 189;
    }
}