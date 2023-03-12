package com.paymentservice.repository;

import com.paymentservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
}
