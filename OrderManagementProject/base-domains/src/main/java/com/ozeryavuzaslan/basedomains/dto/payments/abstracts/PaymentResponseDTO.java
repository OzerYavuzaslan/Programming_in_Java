package com.ozeryavuzaslan.basedomains.dto.payments.abstracts;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.enums.PaymentStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentResponseDTO {
    private long id;
    private long orderId;
    private PaymentProviderType paymentProviderType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
    private PaymentStatus paymentStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @Builder.Default
    private String tmpStr = "STRRRRRRRR";
}