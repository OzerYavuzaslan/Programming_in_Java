package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.service.CacheManagementService;
import com.ozeryavuzaslan.stockservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryPropertySetter categoryPropertySetter;
    private final CacheManagementService cacheManagementService;

    @Value("${category.not.found}")
    private String categoryNotFound;

    @Value("${category.cache.name}")
    private String categoryCacheName;

    @Override
    @CachePut(value = "categories", key = "#categoryWithoutUUIDDTO.name")
    public CategoryDTO saveCategory(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO) {
        categoryPropertySetter.setSomeProperties(categoryWithoutUUIDDTO, true, false);
        cacheManagementService.clearCache(categoryCacheName);
        return modelMapper.map(categoryRepository.save(modelMapper.map(categoryWithoutUUIDDTO, Category.class)), CategoryDTO.class);
    }

    @Override
    @CachePut(value = "categories", key = "#categoryDTO.categoryCode")
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        categoryPropertySetter.setSomeProperties(modelMapper.map(categoryDTO, CategoryWithoutUUIDDTO.class), false, true);
        cacheManagementService.clearCache(categoryCacheName);
        return modelMapper.map(categoryRepository.save(modelMapper.map(getCategory(categoryDTO.getCategoryCode()), Category.class)), CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "categories", key = "#categoryCode")
    public CategoryDTO getByCategoryCode(UUID categoryCode) {
        return modelMapper.map(getCategory(categoryCode), CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "categories", key = "#name")
    public CategoryDTO getByCategoryName(String name) {
        return modelMapper.map(categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(categoryNotFound)), CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "categories", key = "#categoryID")
    public CategoryDTO getByCategoryID(long categoryID) {
        return modelMapper.map(categoryRepository.findById(categoryID).orElseThrow(() -> new CategoryNotFoundException(categoryNotFound)), CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "categories")
    public List<CategoryDTO> getCategoryList() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    @Override
    @CacheEvict(value = "categories", key = "#categoryCode")
    public void deleteCategoryByCategoryCode(UUID categoryCode) {
        categoryRepository.deleteByCategoryCode(categoryCode).orElseThrow(() -> new StockNotFoundException(categoryNotFound));
        cacheManagementService.clearCache(categoryCacheName);
    }

    private CategoryDTO getCategory(UUID categoryCode){
        return modelMapper.map(categoryRepository.findByCategoryCode(categoryCode).orElseThrow(() -> new StockNotFoundException(categoryNotFound)), CategoryDTO.class);
    }
}
