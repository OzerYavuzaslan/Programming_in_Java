package com.biletimcepte.service;

import com.biletimcepte.dto.request.BookingRequest;
import com.biletimcepte.dto.response.BookingResponse;

import java.util.List;

public interface IBookingService {
    BookingResponse create(BookingRequest bookingRequest);
    BookingResponse getBookingByID(int id);
    void changePaymentStatus(int bookingID, int voyageID);
    List<BookingResponse> getList();
}
