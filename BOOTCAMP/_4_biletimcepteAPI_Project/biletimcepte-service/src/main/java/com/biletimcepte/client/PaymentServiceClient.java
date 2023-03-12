package com.biletimcepte.client;

import com.biletimcepte.dto.request.NotificationRequest;
import com.biletimcepte.exception.BookingNotFoundException;
import com.biletimcepte.exception.PaymentAlreadyDoneException;
import com.biletimcepte.model.Booking;
import com.biletimcepte.model.Invoice;
import com.biletimcepte.model.enums.PaymentStatus;
import com.biletimcepte.model.enums.PaymentType;
import com.biletimcepte.repository.IBookingRepository;
import com.biletimcepte.service.IBookingService;
import com.biletimcepte.util.LoggerUtilization;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;

import static com.biletimcepte.util.Constants.*;

@Data
@Service
public class PaymentServiceClient {
    private RabbitTemplate rabbitTemplate;
    private IBookingService iBookingService;
    private IBookingRepository iBookingRepository;

    @Value("${payment.service.url}")
    private String paymentUrl;

    @Autowired
    public PaymentServiceClient(IBookingService iBookingService, IBookingRepository iBookingRepository,
                                RabbitTemplate rabbitTemplate){
        setRabbitTemplate(rabbitTemplate);
        setIBookingService(iBookingService);
        setIBookingRepository(iBookingRepository);
    }

    private static Invoice bookingToInvoice(PaymentType paymentType, Booking booking){
        Invoice invoice = new Invoice();

        invoice.setBookingid(booking.getId());
        invoice.setEMail(booking.getPassengerUser().getEmail());
        invoice.setName(booking.getPassengerUser().getName());
        invoice.setSurname(booking.getPassengerUser().getSurname());
        invoice.setPhoneNumber(booking.getPassengerUser().getPhoneNumber());
        invoice.setPaymentType(paymentType);
        invoice.setPaymentTotal(booking.getBookingTotalPrice());

        return invoice;
    }

    public Invoice processPayment(int bookingId, int voyageId, PaymentType paymentType) {
        Booking booking = getIBookingRepository().findById(bookingId).orElseThrow(() -> new BookingNotFoundException(BOOKING_NOT_FOUND));

        if (booking.getPaymentStatus().equals(PaymentStatus.SUCCESS))
            throw new PaymentAlreadyDoneException(BOOKING_PAYMENT_ALREADY_DONE);

        Invoice invoice = bookingToInvoice(paymentType, booking);

        RestTemplate template = new RestTemplate();
        HttpEntity<Invoice> request = new HttpEntity<>(invoice);
        var response= template.postForObject(paymentUrl, request, Invoice.class);

        getIBookingService().changePaymentStatus(invoice.getBookingid(), voyageId);

        assert response != null;
        getRabbitTemplate().convertAndSend("notification", new NotificationRequest(PAYMENT_SUCCESSFUL + response, "SMS", response.getPhoneNumber()));
        LoggerUtilization.getLogger().log(Level.INFO, "PaymentClientService -> processPayment: " + response.getPhoneNumber());

        return response;
    }
}
