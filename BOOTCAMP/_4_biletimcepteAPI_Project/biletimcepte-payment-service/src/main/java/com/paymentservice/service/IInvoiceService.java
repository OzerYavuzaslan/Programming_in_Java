package com.paymentservice.service;

import com.paymentservice.model.Invoice;

public interface IInvoiceService {
    Invoice save(Invoice invoice);
}