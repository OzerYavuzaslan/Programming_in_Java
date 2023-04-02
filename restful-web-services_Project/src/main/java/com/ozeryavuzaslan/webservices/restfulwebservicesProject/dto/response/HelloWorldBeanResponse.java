package com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldBeanResponse {
    private String message = "HELLO WORLD BEAN!";

    @Override
    public String toString() {
        return "HelloWorldBeanResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
