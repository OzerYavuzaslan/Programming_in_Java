package com.ozeryavuzaslan.basedomains.util;

import java.time.LocalDateTime;

public interface ValidateStock {
    double getDiscountAmount();
    double getDiscountPercentage();
    LocalDateTime getDiscountEndDate();
}