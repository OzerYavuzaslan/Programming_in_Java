package com.ozeryavuzaslan.paymentservice.repository;

import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentInvoice, Long> {
}
