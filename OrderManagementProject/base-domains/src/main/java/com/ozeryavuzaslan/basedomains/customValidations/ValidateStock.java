package com.ozeryavuzaslan.basedomains.customValidations;

import java.time.LocalDateTime;

public interface ValidateStock {
    double getDiscountAmount();
    double getDiscountPercentage();
    LocalDateTime getDiscountEndDate();
}