package com.ozeryavuzaslan.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailsDTO {
    private LocalDateTime timeStamp;
    private String message;
    private String details;

    public void setErrorDetailsProperties(LocalDateTime timeStamp, String message, String details){
        this.setTimeStamp(timeStamp);
        this.setMessage(message);
        this.setDetails(details);
    }
}