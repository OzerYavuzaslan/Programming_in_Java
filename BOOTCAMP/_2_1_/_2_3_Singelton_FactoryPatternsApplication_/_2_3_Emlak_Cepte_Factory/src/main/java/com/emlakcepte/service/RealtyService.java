package com.emlakcepte.service;

import com.emlakcepte.dao.RealtyDao;
import com.emlakcepte.interfaces.IRealtyService;
import com.emlakcepte.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealtyService implements IRealtyService {

    private final RealtyDao realtyDao = new RealtyDao();

    @Autowired //injection
    private UserService userService;
    @Override
    public void createRealty(Realty realty) {
        realtyDao.saveRealty(realty);
        System.out.println("createRealty:: has been created.");
    }
    @Override
    public List<Realty> getAll(){
        return realtyDao.findAll();
    }
    @Override
    public void printAll(List<Realty> realtyList) {
        realtyList.forEach(realty -> System.out.println(realty));
    }
    @Override
    public void getAllByProvince(String province) {
        getAll().stream()
                .filter(realty -> realty.getProvince().equals(province))
                .forEach(realty -> System.out.println(realty));
    }
    @Override
    public void getAllByProvinceAndDistrict(String province, String district){
        getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .filter(realty -> realty.getDistrict().equalsIgnoreCase(district))
                .forEach(System.out::println);
    }
    @Override
    public List<Realty> getAllByUserName(User user){
        return getAll().stream()
                .filter(realty -> realty.getUser().getEmail().equals(user.getEmail()))
                .toList();
    }
    @Override
    public List<Realty> getActiveRealtyByUserName(User user) {
        return getAll().stream()
                .filter(realty -> realty.getUser().getName().equals(user.getName()))
                .filter(realty -> RealtyPublishStatus.ACTIVE.equals(realty.getStatus()))
                .toList();
    }
    @Override
    public Long getRealtyNumberInProvince(String province){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province)).count();
    }
    @Override
    public Long getRealtyNumberInProvince(List<String> provinceList){
        return getAll().stream().filter(realty -> provinceList.contains(realty.getProvince())).count();
    }
    @Override
    public Long getRealtyHousesInProvince(String province, RealtyCategory realtyCategory){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .filter(realty -> realty.getRealtyType().equals(RealtyType.HOUSE))
                .filter(realty -> realty.getCategory().equals(realtyCategory)).count();
    }
    @Override
    public void showCaseByProvince(String province){
        getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .limit(10).forEach(System.out::println);
    }
}