package com.emlakcepte;

import com.emlakcepte.model.*;
import com.emlakcepte.service.RealtyService;
import com.emlakcepte.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        RealtyService realtyService = new RealtyService();

        User user1 = prepareUser("User1", "user1@hotmail.com","user1pass");
        user1.setUserType(UserType.INDIVIDUAL);
        user1.setRealtyList(new ArrayList<>());
        user1.setSearchList(new ArrayList<>());


        User user2 = prepareUser("USer2", "user2@hotmail.com", "user2pass");
        user2.setUserType(UserType.CORPARETE);
        user2.setRealtyList(new ArrayList<>());

        userService.createUser(user1);
        userService.createUser(user2);

        userService.printAllUser();

        Realty r1 = new Realty();
        r1.setProvince("istanbul");
        r1.setDistrict("levent");
        r1.setRealtyType(RealtyType.HOUSE);
        r1.setUser(user1);
        r1.setCategory(RealtyCategory.SALE);

        Realty r2 = new Realty();
        r2.setProvince("ankara");
        r2.setDistrict("levent");
        r2.setRealtyType(RealtyType.HOUSE);
        r2.setUser(user2);
        r2.setCategory(RealtyCategory.SALE);

        realtyService.createRealty(r1);
        realtyService.createRealty(r1);
        realtyService.createRealty(r1);
        realtyService.createRealty(r1);
        realtyService.createRealty(r2);

        System.out.println("Size: " + user1.getRealtyList().size());

        realtyService.getAllByProvinceAndDistrict("istanbul","levent");

        System.out.println("---------------------------------------------------------");

        System.out.println("Istanbul total: " + realtyService.getRealtyNumberInProvince("istanbul"));

        System.out.println("---------------------------------------------------------");

        System.out.println(realtyService.getRealtyHousesInProvince("istanbul", RealtyCategory.SALE));

        System.out.println("-----------------------SHOWCASE----------------------------------");

        realtyService.showCaseByProvince("istanbul");

        System.out.println("---------------------------------------------------------------");
        System.out.println(r1.getUser().getUserType());

        System.out.println("Total izmir ankara istanbul : " + realtyService.getRealtyNumberInProvince(List.of("istanbul","ankara")));

        System.out.println("-------------------------------------------------------");

        Search search1 = new Search();
        search1.setProvince("istanbul");
        search1.setDistrict("maltepe");

        Search search2 = new Search();
        search2.setProvince("ankara");
        search2.setDistrict("kizilay");

        userService.saveSearch(user1, search1);
        userService.saveSearch(user1, search2);
        List<Search> searches = user1.getSearchList();

        for (Search el: searches){
            System.out.println(el);
        }

/*
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

        User userPelin = prepareUser("Pelin", "mimar.pelin@gmail.com", "Pelin123");
        User userSami = new User("Sami", "sami@gmail.com", "123", UserType.INDIVIDUAL, Collections.emptyList());

        /*
         * UserService userService = UserService.getSameInstance();
         *
         * UserService userService1 = UserService.getSameInstance();
         *
         * UserService userService2 = UserService.getDifferentInstance();
         *
         * System.out.println(userService); System.out.println(userService1);
         * System.out.println(userService2);


        // eski yöntem
        //UserService userService = new UserService();

        //Spring bizim yerime create ediyor
        UserService userService = applicationContext.getBean(UserService.class);

        userService.createUser(userPelin);
        userService.createUser(userSami);

        userService.printAllUser();

        System.out.println("-----");

        // userList.add(user);
        // userList.add(userPelin);

        // userList.forEach(usr -> System.out.println(usr.getName()));

        RealtyService realtyService = new RealtyService();

        Realty realty = new Realty(123L, "ZEKERİYAKÖY ' de 1200 M2 Satılık VİLLA", LocalDateTime.now(), userPelin,
                RealtyPublishStatus.ACTIVE);
        realty.setProvince("İstanbul");

        realtyService.createRealty(realty);

        Realty realty1 = new Realty();
        realty1.setNo(124L);
        realty1.setTitle("Büyükdere Ana Cadde üstünde 16.060 m2 kapalı alanlı PLAZA");
        realty1.setStatus(RealtyPublishStatus.ACTIVE);
        realty1.setUser(userPelin);
        realty1.setProvince("İstanbul");

        realtyService.createRealty(realty1);

        Realty favori1 = new Realty();
        favori1.setNo(125L);
        favori1.setTitle("KAVAKPINAR MAHALLESİNDE 2+1 80 M2 ARAKAT İSKANLI");
        favori1.setStatus(RealtyPublishStatus.ACTIVE);
        favori1.setUser(userPelin);
        favori1.setProvince("Ankara");

        realtyService.createRealty(favori1);

        realty.setUser(userSami);
        userSami.setRealtyList(List.of(realty, realty1));

        List<Realty> fovarilerim = new ArrayList<>();
        fovarilerim.add(favori1);
        userSami.setFavoriteRealtyList(fovarilerim);

        // sistemdeki bütün ilanlar

        System.out.println("Bütün ilanlar");

        realtyService.printAll(realtyService.getAll());

        // İstanbuldaki ilanların bulunması

        System.out.println("İstanbul'daki ilanlar");

        realtyService.getAllByProvince("İstanbul");

        realtyService.printAll(realtyService.getAllByUserName(userPelin));

        realtyService.printAll(realtyService.getActiveRealtyByUserName(userSami));

        Message message = new Message("acil dönüş", "ilan ile ilgili bilgilendirme verebilir misiniz?", userPelin,
                userSami);

        userSami.setMessages(List.of(message));
        userPelin.setMessages(List.of(message));
        userSami.getMessages();
 */
    }

    private static User prepareUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.INDIVIDUAL);
        user.setCreateDate(LocalDateTime.now());
        return user;
    }
}