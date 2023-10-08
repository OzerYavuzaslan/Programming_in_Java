package com.ozeryavuzaslan.emailservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.emails.enums.EmailType;
import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentType;
import com.ozeryavuzaslan.emailservice.objectPropertySetter.EmailPropertySetter;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmailPropertySetterImpl implements EmailPropertySetter {
    @Value("${email.stock.subject}")
    private String emailStockSubject;

    @Value("${email.stock.body}")
    private String emailStockBody;

    @Value("${email.payment.subject}")
    private String emailPaymentSubject;

    @Value("${email.payment.body}")
    private String emailPaymentBody;

    @Value("${hash.map.product.code.key}")
    private String productCodeKey;

    @Value("${hash.map.product.name.key}")
    private String productNameKey;

    @Value("${hash.map.email.to.key}")
    private String mailToKey;

    @Value("${hash.map.email.cc.key}")
    private String mailCcKey;

    @Value("${hash.map.email.body.stripe.payment.receipt.url}")
    private String receiptUrl;

    @Value("${hash.map.email.body.full.name}")
    private String fullName;

    @Value("${hash.map.email.body.payment.total.price}")
    private String totalPrice;

    @Value("${hash.map.email.body.payment.currency.type}")
    private String currencyType;

    @Value("${hash.map.email.body.payment.date}")
    private String paymentDate;

    @Value("${email.body.stripe.payment}")
    private String stripePaymentBody;

    @Value("${hash.map.email.body.refund.date}")
    private String refundDate;

    @Value("${hash.map.email.body.refund.refunded.amount}")
    private String refundedAmount;

    @Value("${hash.map.email.body.refund.refunded.amount}")
    private String refundRequestAmount;

    @Value("${email.refund.subject}")
    private String emailRefundSubject;

    @Value("${email.refund.body}")
    private String emailRefundBody;

    @Value("${email.body.stripe.refund}")
    private String stripeRefundBody;

    @Value("${hash.map.order.orderId.key}")
    private String orderID;

    @Value("${hash.map.order.orderDate.key}")
    private String orderDate;

    @Value("${hash.map.order.address1.key}")
    private String address1;

    @Value("${hash.map.order.address2.key}")
    private String address2;

    @Value("${hash.map.order.statusType.key}")
    private String orderStatusType;

    @Value("${email.approved.order.subject}")
    private String emailApprovedOrderSubject;

    @Value("${email.approved.order.body}")
    private String emailApprovedOrderBody;

    @Value("${email.approved.order.body.details}")
    private String emailApprovedOrderBodyDetails;

    @Override
    public void setSomeProperties(EmailDTO emailDTO, HashMap<String, String> emailInfoMap, EmailType emailType, PaymentType paymentType) {
        emailDTO.setMailTo(emailInfoMap.get(mailToKey));
        emailDTO.setMailCc(emailInfoMap.get(mailCcKey));
        emailDTO.setEmailType(emailType);

        String tmpSubject = null;
        String tmpBody = null;

        switch (emailType) {
            case STOCK -> {
                tmpSubject = emailStockSubject;
                tmpBody = emailInfoMap.get(productCodeKey) + " " + emailInfoMap.get(productNameKey) + " " + emailStockBody;
            }
            case ORDER -> {
                switch (OrderStatusType.valueOf(emailInfoMap.get(orderStatusType))){
                    case APPROVED -> {
                        tmpSubject = emailApprovedOrderSubject;
                        tmpBody = String.format(emailApprovedOrderBody, emailInfoMap.get(fullName));
                        tmpBody += String.format(emailApprovedOrderBodyDetails, emailInfoMap.get(orderID), emailInfoMap.get(orderDate), (emailInfoMap.get(address1) + " " + emailInfoMap.get(address2)));
                    }
                    case PREPARING -> {}
                    case IN_CARGO -> {}
                    case CANCELED_BY_CUSTOMER -> {}
                    case CANCELED_PAYMENT_FAILED -> {}
                    case CANCELED_NOT_ENOUGH_STOCK -> {}
                    case CANCELED_SERVICE_DOWN -> {}
                }
            }
            case PAYMENT -> {
                if (paymentType.equals(PaymentType.PAYMENT)) {
                    tmpSubject = emailPaymentSubject;
                    tmpBody = String.format(emailPaymentBody, emailInfoMap.get(fullName));
                    tmpBody += String.format(stripePaymentBody, emailInfoMap.get(totalPrice), emailInfoMap.get(currencyType), emailInfoMap.get(paymentDate), emailInfoMap.get(receiptUrl));
                } else {
                    tmpSubject = emailRefundSubject;
                    tmpBody = String.format(emailRefundBody, emailInfoMap.get(fullName));
                    tmpBody += String.format(stripeRefundBody, emailInfoMap.get(refundedAmount), emailInfoMap.get(currencyType), emailInfoMap.get(refundRequestAmount), emailInfoMap.get(currencyType), emailInfoMap.get(refundDate));
                }
            }
        }

        emailDTO.setBody(tmpBody);
        emailDTO.setSubject(tmpSubject);
    }

    @Override
    public void setSomeProperties(EmailDTO emailDTO) {
        emailDTO.setEmailStatus(EmailStatus.SENT);
    }

    @Override
    public void setSomeProperties(MessagingException exception, EmailDTO emailDTO) {
        emailDTO.setEmailStatus(EmailStatus.FAILED);
        emailDTO.setEmailExceptionMessage(exception.getMessage());
    }
}