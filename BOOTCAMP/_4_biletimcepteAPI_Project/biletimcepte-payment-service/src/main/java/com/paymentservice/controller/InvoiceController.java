package com.paymentservice.controller;

import com.paymentservice.model.Invoice;
import com.paymentservice.service.IInvoiceService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/payments")
public class InvoiceController {
    private IInvoiceService iInvoiceService;

    public InvoiceController(IInvoiceService iInvoiceService) {
        setIInvoiceService(iInvoiceService);
    }

    @PostMapping
    public ResponseEntity<Invoice> processPayment(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(getIInvoiceService().save(invoice));
    }
}
