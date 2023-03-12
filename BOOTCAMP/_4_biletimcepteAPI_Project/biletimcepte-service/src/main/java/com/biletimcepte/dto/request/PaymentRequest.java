package com.biletimcepte.dto.request;

import com.biletimcepte.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    public int bookingid;
    public int voyageId;
    public PaymentType paymentType;
}
