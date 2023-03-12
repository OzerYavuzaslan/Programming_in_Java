package com.emlakcepte.service;

import com.emlakcepte.model.Realty;
import com.emlakcepte.model.RealtyPublishStatus;
import com.emlakcepte.model.Search;
import com.emlakcepte.model.User;
import com.emlakcepte.model.enums.RealtyCategory;
import com.emlakcepte.model.enums.RealtyType;
import com.emlakcepte.model.enums.UserType;
import com.emlakcepte.repository.RealtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RealtyService {
   // @Autowired
    private final RealtyRepository realtyRepository = new RealtyRepository();

    private final List<Search> searchHistoryList = new ArrayList<>();

    @Autowired //injection
    private UserService userService;

    public void createRealty(Realty realty) {
        if (realty.getUser().getUserType().equals(UserType.INDIVIDUAL) && realty.getRealtyType().equals(RealtyType.HOUSE)) {
            if (realty.getUser().getRealtyList().size() < 3){
                realtyRepository.saveRealty(realty);
                System.out.println(realty.getUser().getName() + " İlan eklendi " + realty.getTitle());
            }
            else
                System.out.println("Bireysel kullanıclar en fazla 3 ilan girebilir.");
        }
        else{
            realtyRepository.saveRealty(realty);
            System.out.println(realty.getRealtyType() + " İlan eklendi " + realty.getTitle());
        }
    }

    public List<Realty> getAll(){
        return realtyRepository.findAll();
    }

    public void printAll(List<Realty> realtyList) {
        realtyList.forEach(realty -> System.out.println(realty));
    }

    public List<Realty> getAllByProvince(String province) {
        return getAll().stream()
                .filter(realty -> realty.getProvince().equals(province))
                .toList();
            //    .forEach(realty -> System.out.println(realty));
    }

    public List<Realty> getAllByProvinceAndDistrict(String province, String district){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .filter(realty -> realty.getDistrict().equalsIgnoreCase(district)).toList();
                //.forEach(System.out::println);
    }

    public List<Realty> getAllByUserName(User user){
        return getAll().stream()
                .filter(realty -> realty.getUser().getName().equals(user.getName()))
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

    public List<Realty> showCaseByProvince(String province){
        return getAll().stream().filter(realty -> realty.getProvince().equalsIgnoreCase(province))
                .limit(10).toList();
                //.forEach(System.out::println)
    }

    public List<Search> getAllSearchHistory(User user) {
        return searchHistoryList.stream()
                .filter(history -> history.getUser().getName().equals(user.getName())).toList();
    }
}