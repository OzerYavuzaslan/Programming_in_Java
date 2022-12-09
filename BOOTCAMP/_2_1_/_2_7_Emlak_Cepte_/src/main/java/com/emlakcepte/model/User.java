package com.emlakcepte.model;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private List<Realty> realtyList;
    private List<Realty>  favoriteRealtyList;
    private List<Message> messages;
    private LocalDateTime createDate;
    private List<Search> searchList;

    public User() {
    }
    public User(String name, String email, String password, UserType type, List<Realty> realtyList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = type;
        this.realtyList = realtyList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public List<Realty> getRealtyList() {
        return realtyList;
    }
    public void setRealtyList(List<Realty> realtyList) {
        this.realtyList = realtyList;
    }
    public List<Realty> getFavoriteRealtyList() {
        return favoriteRealtyList;
    }
    public void setFavoriteRealtyList(List<Realty> favoriteRealtyList) {
        this.favoriteRealtyList = favoriteRealtyList;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<Search> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Search> searchList) {
        this.searchList = searchList;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", mail=" + email + ", password=" + password + ", type=" + userType +   "createDate="
                + createDate + "]";
    }
}