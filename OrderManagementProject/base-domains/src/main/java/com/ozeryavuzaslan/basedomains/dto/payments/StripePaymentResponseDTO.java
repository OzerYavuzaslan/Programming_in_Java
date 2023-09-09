package com.ozeryavuzaslan.basedomains.dto.payments;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozeryavuzaslan.basedomains.dto.payments.abstracts.PaymentResponseDTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StripePaymentResponseDTO extends PaymentResponseDTO {
    @JsonIgnore
    private String userID;
    private String receipt_url;
}
