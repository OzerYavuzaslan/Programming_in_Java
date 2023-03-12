package com.emlakcepte.controller;

import com.emlakcepte.model.Payment;
import com.emlakcepte.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/{cardNo}/{extensionAmount}")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment, @PathVariable String cardNo,@PathVariable Integer extensionAmount) {
        paymentService.processPayment(payment,cardNo,extensionAmount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
