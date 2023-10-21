package com.ozeryavuzaslan.basedomains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailsDTO implements Serializable {
    @JsonProperty(value = "timestamp")
    private LocalDateTime timeStamp;
    private String message;
    private String details;
    private boolean status = false;

    public void setErrorDetailsProperties(LocalDateTime timeStamp, String message, String details){
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }
}