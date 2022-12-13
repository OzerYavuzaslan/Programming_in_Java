package com.emlakcepte.interfaces;

import com.emlakcepte.model.Realty;
import com.emlakcepte.model.RealtyCategory;
import com.emlakcepte.model.User;

import java.util.List;

public interface IRealtyService {
    void createRealty(Realty realty);
    List<Realty> getAll();
    void printAll(List<Realty> realtyList);
    void getAllByProvince(String province);
    void getAllByProvinceAndDistrict(String province, String district);
    List<Realty> getAllByUserName(User user);
    List<Realty> getActiveRealtyByUserName(User user);
    Long getRealtyNumberInProvince(String province);
    Long getRealtyNumberInProvince(List<String> provinceList);
    Long getRealtyHousesInProvince(String province, RealtyCategory realtyCategory);
    void showCaseByProvince(String province);
}
