package com.biletimcepte.service;

import com.biletimcepte.converter.BookingConverter;
import com.biletimcepte.converter.TicketConverter;
import com.biletimcepte.dto.request.BookingRequest;
import com.biletimcepte.dto.request.TicketRequest;
import com.biletimcepte.dto.response.BookingResponse;
import com.biletimcepte.exception.*;
import com.biletimcepte.model.Booking;
import com.biletimcepte.model.Ticket;
import com.biletimcepte.model.User;
import com.biletimcepte.model.Voyage;
import com.biletimcepte.model.enums.GenderType;
import com.biletimcepte.model.enums.PaymentStatus;
import com.biletimcepte.model.enums.UserType;
import com.biletimcepte.repository.IBookingRepository;
import com.biletimcepte.repository.IUserRepository;
import com.biletimcepte.repository.IVoyageRepository;
import com.biletimcepte.util.LoggerUtilization;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;

import static com.biletimcepte.util.Constants.*;

@Data
@Service
public class BookingService implements IBookingService{
    private IVoyageService iVoyageService;
    private IUserRepository iUserRepository;
    private TicketConverter ticketConverter;
    private BookingConverter bookingConverter;
    private IVoyageRepository iVoyageRepository;
    private IBookingRepository iBookingRepository;

    @Autowired
    public BookingService(IUserRepository iUserRepository, IBookingRepository iBookingRepository,
                          IVoyageRepository iVoyageRepository, BookingConverter bookingConverter,
                          TicketConverter ticketConverter, IVoyageService iVoyageService) {
        setIVoyageService(iVoyageService);
        setIUserRepository(iUserRepository);
        setTicketConverter(ticketConverter);
        setBookingConverter(bookingConverter);
        setIVoyageRepository(iVoyageRepository);
        setIBookingRepository(iBookingRepository);
    }

    @Override
    @Transactional
    public BookingResponse create(BookingRequest bookingRequest) {
        validateBooking(bookingRequest);

        User user = getUser(bookingRequest);
        Voyage voyage = getVoyage(bookingRequest);

        checkAvailableSeats(voyage, bookingRequest);
        validatePassengerAvailability(bookingRequest, user, voyage);

        Booking booking = new Booking();
        List<Ticket> ticketList = getTicketConverter().convert(bookingRequest);

        setupBooking(user, voyage, booking, ticketList);
        getIBookingRepository().save(booking);

        LoggerUtilization.getLogger().log(Level.INFO, "BookingService -> create successful " +
                bookingRequest.getPassengerEmail()+ " | " +
                bookingRequest.getFromCity() + " --> " +
                bookingRequest.getToCity() + " --> " +
                bookingRequest.getVoyageDateTime());

        return getBookingConverter().convert(booking);
    }

    @Override
    public BookingResponse getBookingByID(int id) {
        return getBookingConverter()
                .convert(getIBookingRepository()
                        .findById(id)
                        .orElseThrow(() ->
                                new BookingNotFoundException(BOOKING_NOT_FOUND)));
    }

    @Override
    @Transactional
    public void changePaymentStatus(int bookingID, int voyageID) {
        Booking booking = getIBookingRepository().findById(bookingID)
                .orElseThrow(() ->
                        new BookingNotFoundException(BOOKING_NOT_FOUND));

        booking.setPaymentStatus(PaymentStatus.SUCCESS);

        Voyage voyage = getIVoyageService().getVoyageById(voyageID);
        voyage.setTotalPrice(voyage.getTotalPrice() + booking.getBookingTotalPrice());
        getIVoyageRepository().save(voyage);
        getIBookingRepository().save(booking);
        LoggerUtilization.getLogger().log(Level.INFO, "BookingService -> changePaymentStatus successful");
    }

    @Override
    public List<BookingResponse> getList() {
        return getBookingConverter().convert(getIBookingRepository().findAll());
    }

    private void setupBooking(User user, Voyage voyage, Booking booking, List<Ticket> ticketList){
        booking.setPassengerUser(user);
        booking.setTicketList(ticketList);
        booking.getTicketList().forEach(
                ticket -> {
                    ticket.setBooking(booking);
                    ticket.setVoyage(voyage);
                    ticket.setPricePerTicket(ticket.getVoyage().getPricePerTicket());
                    booking.setBookingTotalPrice(booking.getBookingTotalPrice() + ticket.getPricePerTicket());
                    ticket.getVoyage().setAvailableSeats(ticket.getVoyage().getAvailableSeats() - 1);
                });
    }

    private User getUser(BookingRequest bookingRequest){
        return getIUserRepository()
                .selectByEmail(bookingRequest.getPassengerEmail())
                .orElseThrow(() ->
                        new UserNotFoundException(USER_NOT_FOUND));
    }

    private Voyage getVoyage(BookingRequest bookingRequest){
        return getIVoyageRepository()
                .findVoyages(bookingRequest.getFromCity(), bookingRequest.getToCity(),
                        bookingRequest.getVoyageDateTime(), bookingRequest.getTravelType().toString())
                .orElseThrow(() ->
                        new VoyageNotFoundException(VOYAGE_NOT_FOUND));
    }

    private static void checkAvailableSeats(Voyage voyage, BookingRequest bookingRequest) {
        if (voyage.getAvailableSeats() < bookingRequest.getBookingTicketList().size()) {
            LoggerUtilization.getLogger().log(Level.WARNING, NOT_ENOUGH_SEATS_FOUND);
            throw new NotEnoughAvailableSeatsException(NOT_ENOUGH_SEATS_FOUND);
        }
    }

    private static void validateBooking(BookingRequest bookingRequest) {
        if (bookingRequest.getBookingTicketList() == null || bookingRequest.getBookingTicketList().size() == 0) {
            LoggerUtilization.getLogger().log(Level.SEVERE, LIMITED_MAX_TICKET_FOR_INDIVIDUALS + LIMITED_MAX_TICKET_NUM_FOR_INDIVIDUAL_PASSENGER + TICKET);
            throw new EmptyTicketListException(NO_TICKET_INFO_FOUND);
        }
    }

    private static void validatePassengerAvailability(BookingRequest bookingRequest, User user, Voyage voyage) {
        var latestNumOfTickets = user.getBookingList()
                .stream()
                .flatMap(booking -> booking
                        .getTicketList()
                        .stream()
                        .filter(ticket ->
                                Integer.valueOf(ticket.getVoyage().getId())
                                        .equals(voyage.getId())))
                .count();

        if (user.getUserType().equals(UserType.INDIVIDUAL)) {
            if (bookingRequest.getBookingTicketList().size() + latestNumOfTickets > LIMITED_MAX_TICKET_NUM_FOR_INDIVIDUAL_PASSENGER) {
                LoggerUtilization.getLogger().log(Level.SEVERE, LIMITED_MAX_TICKET_FOR_INDIVIDUALS + LIMITED_MAX_TICKET_NUM_FOR_INDIVIDUAL_PASSENGER + TICKET);
                throw new NotEnoughAvailableSeatsException(LIMITED_MAX_TICKET_FOR_INDIVIDUALS + LIMITED_MAX_TICKET_NUM_FOR_INDIVIDUAL_PASSENGER + TICKET);
            }

            int numOfMalePassengers = 0;

            for (TicketRequest ticketRequest : bookingRequest.getBookingTicketList()) {
                if (ticketRequest.getGenderType() == GenderType.MALE)
                    numOfMalePassengers++;

                if (numOfMalePassengers > MAX_MALE_PASSENGERS_TO_BUY_TICKETS_PER_BOOKING) {
                    LoggerUtilization.getLogger().log(Level.SEVERE, LIMITED_MAX_MALE_PASSENGERS + MAX_MALE_PASSENGERS_TO_BUY_TICKETS_PER_BOOKING + TICKET);
                    throw new MaxAllowedMalePassengerNumException(LIMITED_MAX_MALE_PASSENGERS + MAX_MALE_PASSENGERS_TO_BUY_TICKETS_PER_BOOKING + TICKET);
                }
            }
        }

        if (user.getUserType().equals(UserType.CORPORATE))
            if (bookingRequest.getBookingTicketList().size() + latestNumOfTickets > LIMITED_MAX_TICKET_NUM_FOR_CORPORATE_PASSENGER) {
                LoggerUtilization.getLogger().log(Level.SEVERE, LIMITED_MAX_TICKET_FOR_CORPORATE + LIMITED_MAX_TICKET_NUM_FOR_CORPORATE_PASSENGER + TICKET);
                throw new NotEnoughAvailableSeatsException(LIMITED_MAX_TICKET_FOR_CORPORATE + LIMITED_MAX_TICKET_NUM_FOR_CORPORATE_PASSENGER + TICKET);
        }
    }
}