package com.ozeryavuzaslan.paymentservice.repository;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import com.ozeryavuzaslan.paymentservice.model.PaymentInvoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentInvoice, Long> {
    Optional<PaymentInvoice> findByOrderid(long orderid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PaymentInvoice p " +
            "SET p.refundid = :refundId, " +
            "    p.refundedAmount = :refundedAmount, " +
            "    p.refundRequestAmount = :refundRequestAmount, " +
            "    p.paymentType = :paymentType, " +
            "    p.refundDate = :refundDate " +
            "WHERE p.orderid = :orderId")
    void updatePaymentInvoice(@Param("orderId") long orderId,
                              @Param("refundId") String refundId,
                              @Param("refundedAmount") double refundedAmount,
                              @Param("refundRequestAmount") double refundRequestAmount,
                              @Param("paymentType") PaymentType paymentType,
                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Param("refundDate") LocalDateTime refundDate);
}
