package com.emlakcepte;

import com.emlakcepte.factory.RealtyServiceFactory;
import com.emlakcepte.factory.UserServiceFactory;
import com.emlakcepte.interfaces.IRealtyService;
import com.emlakcepte.interfaces.IUserService;
import com.emlakcepte.model.Realty;
import com.emlakcepte.model.User;

public class Main {
    public static void main(String[] args) {
        RealtyServiceFactory realtyServiceFactory = new RealtyServiceFactory();
        IRealtyService realtyService = realtyServiceFactory.getRealtyService("realtyservice");

        UserServiceFactory userServiceFactory = new UserServiceFactory();
        IUserService userService = userServiceFactory.getUserService("userservice");

        Realty realty1 = new Realty();
        User user1 = new User();

        realtyService.createRealty(realty1);
        userService.createUser(user1);

        userService.getAllUser();
        realtyService.getAll();
    }
}