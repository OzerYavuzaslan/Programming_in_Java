package com.emlakcepte.service;

import com.emlakcepte.dao.RealtyDao;
import com.emlakcepte.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealtyService {
    private static RealtyService realtyService = null;
    private final RealtyDao realtyDao = new RealtyDao();

    public static RealtyService getRealtyService(){
        if (realtyService == null)
            realtyService = new RealtyService();

        return realtyService;
    }

    public void createRealty(Realty realty) {
        if (realty.getUser().getUserType().equals(UserType.INDIVIDUAL) && realty.getRealtyType().equals(RealtyType.HOUSE)) {
            if (realty.getUser().getRealtyList().size() < 3){
                realtyDao.saveRealty(realty);
                System.out.println(realty.getUser().getName() + " İlan eklendi " + realty.getTitle());
            }
            else
                System.out.println("Bireysel kullanıclar en fazla 3 ilan girebilir.");
        }
        else{
            realtyDao.saveRealty(realty);
            System.out.println(realty.getRealtyType() + " İlan eklendi " + realty.getTitle());
        }
    }

    public List<Realty> getAll(){
        return realtyDao.findAll();
    }

    public void printAll(List<Realty> realtyList) {
        realtyList.forEach(realty -> System.out.println(realty));
    }

    public void getAllByProvince(String province) {
        getAll().stream()
                .filter(realty -> realty.getProvince().equals(province))
                .forEach(realty -> System.out.println(realty));
    }

    public void getAllByProvinceAndDistrict(String province, String district){
        getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .filter(realty -> realty.getDistrict().equalsIgnoreCase(district))
                .forEach(System.out::println);
    }

    public List<Realty> getAllByUserName(User user){
        return getAll().stream()
                .filter(realty -> realty.getUser().getEmail().equals(user.getEmail()))
                .toList();
    }

    public List<Realty> getActiveRealtyByUserName(User user) {
        return getAll().stream()
                .filter(realty -> realty.getUser().getName().equals(user.getName()))
                .filter(realty -> RealtyPublishStatus.ACTIVE.equals(realty.getStatus()))
                .toList();
    }

    public Long getRealtyNumberInProvince(String province){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province)).count();
    }

    public Long getRealtyNumberInProvince(List<String> provinceList){
        return getAll().stream().filter(realty -> provinceList.contains(realty.getProvince())).count();
    }

    public Long getRealtyHousesInProvince(String province, RealtyCategory realtyCategory){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .filter(realty -> realty.getRealtyType().equals(RealtyType.HOUSE))
                .filter(realty -> realty.getCategory().equals(realtyCategory)).count();
    }

    public void showCaseByProvince(String province){
        getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .limit(10).forEach(System.out::println);
    }
}