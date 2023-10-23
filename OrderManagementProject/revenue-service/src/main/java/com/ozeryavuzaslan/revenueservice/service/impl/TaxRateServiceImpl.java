package com.ozeryavuzaslan.revenueservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.revenues.enums.TaxRateType;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.revenueservice.exception.TaxRateNotFoundException;
import com.ozeryavuzaslan.revenueservice.model.TaxRate;
import com.ozeryavuzaslan.revenueservice.repository.TaxRateRepository;
import com.ozeryavuzaslan.revenueservice.service.TaxRateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxRateServiceImpl implements TaxRateService {
    private final ModelMapper modelMapper;
    private final TaxRateRepository taxRateRepository;
    private final CacheManagementService cacheManagementService;
    private static boolean isCacheRefresh = false;

    @Value("${tax.rate.cache.name}")
    private String taxRateCacheName;

    @Value("${tax.rate.not.found}")
    private String taxRateNotFound;

    @Value("${tax.rates.not.found}")
    private String taxRatesNotFound;

    @Override
    @CachePut(value = "taxes", key = "#taxRateDTO.year + '-' + #taxRateDTO.month + '-' + #taxRateDTO.taxRateType")
    public TaxRateDTO insertTaxRate(TaxRateDTO taxRateDTO) {
        isCacheRefresh = false;
        return modelMapper
                .map(taxRateRepository
                        .save(modelMapper
                                .map(taxRateDTO, TaxRate.class)),
                        TaxRateDTO.class);
    }

    @Override
    @Transactional
    @Cacheable(value = "taxes")
    public List<TaxRateDTO> taxRateBulkInsert(List<TaxRateDTO> taxRateDTOList) {
        List<TaxRate> taxRateList = new ArrayList<>();

        for (TaxRateDTO taxRateDTO : taxRateDTOList)
            taxRateList.add(modelMapper.map(taxRateDTO, TaxRate.class));

        isCacheRefresh = false;
        return taxRateRepository.saveAll(taxRateList)
                .stream()
                .map(taxRate -> modelMapper.map(taxRate, TaxRateDTO.class))
                .toList();
    }

    @Override
    @CachePut(value = "taxes", key = "#taxRateDTO.year + '-' + #taxRateDTO.month + '-' + #taxRateDTO.taxRateType")
    public TaxRateDTO updateTaxRate(TaxRateDTO taxRateDTO) {
        TaxRate existingTaxRate = getSpecificTaxRate(taxRateDTO.getId());
        modelMapper.map(taxRateDTO, existingTaxRate);
        TaxRate updatedTaxRate = taxRateRepository.save(existingTaxRate);
        isCacheRefresh = false;
        return modelMapper.map(updatedTaxRate, TaxRateDTO.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "taxes", allEntries = true)
    public List<TaxRateDTO> taxRateBulkUpdate(List<TaxRateDTO> taxRateDTOList) {
        List<Long> taxRateIds = taxRateDTOList.stream().map(TaxRateDTO::getId).collect(Collectors.toList());
        Optional<List<TaxRate>> optionalTaxRateList = taxRateRepository.findByIdIn(taxRateIds);

        if (optionalTaxRateList.isEmpty())
            throw new TaxRateNotFoundException(taxRatesNotFound);

        List<TaxRate> existingTaxRates = optionalTaxRateList.get();

        if (existingTaxRates.size() != taxRateDTOList.size()) {
            List<TaxRateDTO> existingDTOs = existingTaxRates.stream().map(taxRate -> modelMapper.map(taxRate, TaxRateDTO.class)).toList();

            for (TaxRateDTO dto : taxRateDTOList) {
                if (!existingDTOs.contains(dto)) {
                    String taxInfo = " (" + dto.getMonth() + " " + dto.getYear() + " " + dto.getTaxRateType() + ")";
                    throw new TaxRateNotFoundException(taxRateNotFound + taxInfo);
                }
            }
        }

        Map<Long, TaxRateDTO> dtoMap = taxRateDTOList.stream().collect(Collectors.toMap(TaxRateDTO::getId, dto -> dto));

        for (TaxRate taxRate : existingTaxRates)
            modelMapper.map(dtoMap.get(taxRate.getId()), taxRate);

        taxRateRepository.saveAll(existingTaxRates);
        isCacheRefresh = true;
        return existingTaxRates.stream().map(taxRate -> modelMapper.map(taxRate, TaxRateDTO.class)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "taxes", key = "#taxRateDTO.year + '-' + #taxRateDTO.month + '-' + #taxRateDTO.taxRateType")
    public TaxRateDTO deleteSpecificTaxRate(TaxRateDTO taxRateDTO) {
        TaxRate taxRate = getSpecificTaxRate(taxRateDTO.getId());
        taxRateRepository.delete(taxRate);
        isCacheRefresh = false;
        return taxRateDTO;
    }



    @Override
    @Cacheable(value = "taxes")
    @SuppressWarnings("ConstantConditions")
    public List<TaxRateDTO> getAllTaxRates(Pageable pageable) {
        return taxRateRepository
                .findAll(pageable)
                .stream()
                .map(taxRate -> modelMapper.map(taxRate, TaxRateDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "taxes", key = "#taxYear + '-' + #taxMonth + '-' + #taxRateType")
    public TaxRateDTO getTaxRate(int taxYear, int taxMonth, TaxRateType taxRateType) {
        return modelMapper.map(getSpecificTaxRate(taxYear, taxMonth, taxRateType), TaxRateDTO.class);
    }

    @Override
    public void checkTaxRateServiceCacheState(){
        isCacheRefresh = cacheManagementService.releaseCache(isCacheRefresh, taxRateCacheName);
    }

    private TaxRate getSpecificTaxRate(long taxID){
        return taxRateRepository.findById(taxID).orElseThrow(() -> new TaxRateNotFoundException(taxRateNotFound));
    }

    private TaxRate getSpecificTaxRate(int taxYear, int taxMonth, TaxRateType taxRateType){
        return taxRateRepository.findByYearAndMonthAndTaxRateType(taxYear, taxMonth, taxRateType).orElseThrow(() -> new TaxRateNotFoundException(taxRateNotFound));
    }
}
