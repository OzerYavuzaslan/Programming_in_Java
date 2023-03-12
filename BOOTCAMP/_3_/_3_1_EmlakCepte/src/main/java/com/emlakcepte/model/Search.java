package com.emlakcepte.model;

public class Search {
    private String province;
    private String district;
    private String searchName;
    private User user;

    public Search(){

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Search{" +
                "province='" + province + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
