package com.biletimcepte.controller;

import com.biletimcepte.client.PaymentServiceClient;
import com.biletimcepte.dto.request.BookingRequest;
import com.biletimcepte.dto.request.PaymentRequest;
import com.biletimcepte.dto.response.BookingResponse;
import com.biletimcepte.model.Invoice;
import com.biletimcepte.service.IBookingService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private IBookingService iBookingService;
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    public BookingController(IBookingService iBookingService, PaymentServiceClient paymentServiceClient){
        setIBookingService(iBookingService);
        setPaymentServiceClient(paymentServiceClient);
    }

    @GetMapping("/bookingList")
    public ResponseEntity<List<BookingResponse>> getList(){
        return new ResponseEntity<>(getIBookingService().getList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getByID(@PathVariable int id){
        return ResponseEntity.ok(getIBookingService().getBookingByID(id));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(getIBookingService().create(bookingRequest));
    }

    @PostMapping("/payment")
    public ResponseEntity<Invoice> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Invoice invoice = paymentServiceClient.processPayment(paymentRequest.getBookingid(), paymentRequest.getVoyageId(), paymentRequest.getPaymentType());
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }
}
