package com.emlakcepte.controller;

import com.emlakcepte.enums.RealtyCategory;
import com.emlakcepte.model.Realty;
import com.emlakcepte.service.RealtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/realty")
public class RealtyController {
    @Autowired
    private RealtyService realtyService;

    @GetMapping
    public ResponseEntity<List<Realty>> getAll(){
        return new ResponseEntity<>(realtyService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Realty> create(@RequestBody Realty realty){
        System.out.println("Realty Address: " + realty);
        realtyService.createRealty(realty);
        return new ResponseEntity<>(realty, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{province}/{district}")
    public ResponseEntity<List<Realty>> getAllByProvinceAndDistrict(@PathVariable String province, @PathVariable String district){
        return new ResponseEntity<>(realtyService.getAllByProvinceAndDistrict(province, district), HttpStatus.OK);
    }

   @GetMapping(value = "/showcase/{province}")
   public ResponseEntity<List<Realty>> showCaseByProvince(@PathVariable String province){
        return new ResponseEntity<>(realtyService.showCaseByProvince(province), HttpStatus.OK);
   }

   @GetMapping("/count/{province}")
    public ResponseEntity<Long> getRealtyNumberInProvince(@PathVariable String province){
        return new ResponseEntity<>(realtyService.getRealtyNumberInProvince(province), HttpStatus.OK);
   }

   @GetMapping("/count/{province}/{category}")
   public ResponseEntity<Long> getRealtyHousesInProvince(@PathVariable String province, @PathVariable RealtyCategory realtyCategory){
        return new ResponseEntity<>(realtyService.getRealtyHousesInProvince(province, realtyCategory), HttpStatus.OK);
   }
}
