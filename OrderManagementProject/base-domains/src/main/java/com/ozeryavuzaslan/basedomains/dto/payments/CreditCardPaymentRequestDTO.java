package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditCardPaymentRequestDTO extends PaymentRequestDTO {
    @Override
    public PaymentProviderType getPaymentProviderType() {
        return PaymentProviderType.CREDIT_CARD;
    }
}
