package com.ozeryavuzaslan.webservices.restfulwebservicesProject.service;

import com.ozeryavuzaslan.webservices.restfulwebservicesProject.model.User;

public interface UserFinder {
    User findSpecificUser(int id);
    User findSpecificUser(String eMail);
}
