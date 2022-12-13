package com.emlakcepte.factory;

import com.emlakcepte.interfaces.IRealtyService;
import com.emlakcepte.service.RealtyService;

public class RealtyServiceFactory {
    public IRealtyService getRealtyService(String realtyServiceType){
        if (realtyServiceType == null)
            return null;

        if (realtyServiceType.equalsIgnoreCase("realtyservice"))
            return new RealtyService();

        return null;
    }
}
