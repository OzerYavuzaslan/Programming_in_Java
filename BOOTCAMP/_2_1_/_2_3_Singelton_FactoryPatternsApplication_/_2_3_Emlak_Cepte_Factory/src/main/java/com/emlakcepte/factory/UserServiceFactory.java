package com.emlakcepte.factory;

import com.emlakcepte.interfaces.IUserService;
import com.emlakcepte.service.UserService;

public class UserServiceFactory {
    public IUserService getUserService(String userServiceType){
        if (userServiceType == null)
            return null;

        if (userServiceType.equalsIgnoreCase("userservice"))
            return new UserService();

        return null;
    }
}
