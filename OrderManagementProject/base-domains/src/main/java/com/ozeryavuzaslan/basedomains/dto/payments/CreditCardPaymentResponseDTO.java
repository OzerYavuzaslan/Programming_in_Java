package com.ozeryavuzaslan.basedomains.dto.payments;


import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditCardPaymentResponseDTO extends PaymentResponseDTO {
}
