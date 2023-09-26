package com.ozeryavuzaslan.basedomains.dto.payments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentProviderType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StripePaymentRequestDTO extends PaymentRequestDTO {
    @JsonIgnore
    private String userid;

    @Override
    public PaymentProviderType getPaymentProviderType() {
        return PaymentProviderType.STRIPE;
    }
}
