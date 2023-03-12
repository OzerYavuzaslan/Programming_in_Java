package com.paymentservice.service;

import com.paymentservice.model.Invoice;
import com.paymentservice.repository.IInvoiceRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class InvoiceService implements IInvoiceService {
    private IInvoiceRepository iInvoiceRepository;

    @Autowired
    public InvoiceService(IInvoiceRepository iInvoiceRepository) {
        setIInvoiceRepository(iInvoiceRepository);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return getIInvoiceRepository().save(invoice);
    }
}
