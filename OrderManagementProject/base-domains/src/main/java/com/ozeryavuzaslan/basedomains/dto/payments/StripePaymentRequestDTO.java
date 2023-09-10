package com.ozeryavuzaslan.basedomains.dto.payments;

import com.ozeryavuzaslan.basedomains.dto.enums.PaymentProviderType;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.ozeryavuzaslan.basedomains.util.Constants.USER_ID_NOT_VALID;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StripePaymentRequestDTO extends PaymentRequestDTO {
    @NotNull(message = USER_ID_NOT_VALID)
    @NotEmpty(message = USER_ID_NOT_VALID)
    @NotBlank(message = USER_ID_NOT_VALID)
    private String userid;

    @Override
    public PaymentProviderType getPaymentProviderType() {
        return PaymentProviderType.STRIPE;
    }
}
