package com.ozeryavuzaslan.employeeservice.util;

import org.springframework.stereotype.Component;

@Component
public class JSONStringFinder {
    public String returnProperJSONStr(String jsonStr){
        int beginningOjJsonIndex = jsonStr.indexOf('"');
        return jsonStr.substring(beginningOjJsonIndex + 1, jsonStr.length() - 1);
    }
}