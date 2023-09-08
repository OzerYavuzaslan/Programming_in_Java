package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PayAtHomePaymentRequestDTO extends PaymentRequestDTO {
    @Override
    public PaymentProviderType getPaymentProviderType() {
        return PaymentProviderType.PAY_AT_HOME;
    }
}
