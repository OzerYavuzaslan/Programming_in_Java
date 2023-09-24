package com.ozeryavuzaslan.paymentservice.repository;

import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentInvoice, Long> {
    Optional<PaymentInvoice> findByOrderid(long orderid);
}
