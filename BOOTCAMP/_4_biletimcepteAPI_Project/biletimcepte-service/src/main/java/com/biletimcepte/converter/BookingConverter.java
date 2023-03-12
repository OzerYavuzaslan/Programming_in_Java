package com.biletimcepte.converter;

import com.biletimcepte.dto.response.BookingResponse;
import com.biletimcepte.model.Booking;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class BookingConverter {
    private TicketConverter ticketConverter;

    @Autowired
    public BookingConverter(TicketConverter ticketConverter){
        setTicketConverter(ticketConverter);
    }

    public BookingResponse convert(Booking booking){
        BookingResponse response = new BookingResponse();

        response.setBookingTotalPrice(booking.getBookingTotalPrice());
        response.setTicketResponseList(getTicketConverter().convert(booking.getTicketList()));
        response.setCreationDateTime(LocalDateTime.now());
        response.setPassengerEmail(booking.getPassengerUser().getEmail());

        return response;
    }
/*
    public Booking convert(BookingRequest bookingRequest) {
        Booking booking = new Booking();

        booking.setAddDate(LocalDateTime.now());
        booking.setBookingTotalPrice(booking.getBookingTotalPrice());
        booking.setPassengerUser(booking.getPassengerUser());
        booking.setTicketList(booking.getTicketList());

        return booking;
    }

    public Booking convert(BookingRequest bookingRequest, Booking booking,
                           double bookingTotalPrice, User user, List<Ticket> ticketList) {
        booking.setUpdDate(LocalDateTime.now());
        booking.setBookingTotalPrice(bookingTotalPrice);
        booking.setPassengerUser(user);
        booking.setTicketList(ticketList);

        return booking;
    }
*/
    public List<BookingResponse> convert(List<Booking> bookingList) {
        List<BookingResponse> bookingResponses = new ArrayList<>();

        for (Booking booking : bookingList)
            bookingResponses.add(convert(booking));

        return bookingList.stream().map(this::convert).toList();
    }
}
