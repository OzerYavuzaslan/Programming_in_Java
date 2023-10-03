package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaypalPaymentRequestDTO extends PaymentRequestDTO {
    @Override
    public PaymentProviderType getPaymentProviderType() {
        return PaymentProviderType.PAYPAL;
    }
}
