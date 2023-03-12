package com.biletimcepte.model;

import com.biletimcepte.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Serializable {
    private PaymentType paymentType;
    private String name;
    private String surname;
    private String eMail;
    private String phoneNumber;
    private double paymentTotal;
    private int bookingid;

    @Override
    public String toString() {
        return "Invoice{" +
                "paymentType=" + paymentType +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", paymentTotal=" + paymentTotal +
                ", bookingid=" + bookingid +
                '}';
    }
}
