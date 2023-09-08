package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private long orderId;
    private PaymentType paymentType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double taxRate;
    private double totalPrice;
    private double totalPriceWithoutTax;
}
