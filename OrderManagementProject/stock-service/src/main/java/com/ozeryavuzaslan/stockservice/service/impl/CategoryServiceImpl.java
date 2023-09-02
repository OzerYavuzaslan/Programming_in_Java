package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryPropertySetter categoryPropertySetter;

    @Value("${category.not.found}")
    private String categoryNotFound;

    @Override
    @CachePut(value = "Category", key = "#Category.name")
    public CategoryDTO saveCategory(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO) {
        categoryPropertySetter.setSomeProperties(categoryWithoutUUIDDTO, true, false);

        return modelMapper
                .map(categoryRepository
                        .save(modelMapper
                                .map(categoryWithoutUUIDDTO, Category.class)),
                        CategoryDTO.class);
    }

    @Override
    @CachePut(value = "Category", key = "#Category.categoryCode")
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        categoryPropertySetter.setSomeProperties(modelMapper.map(categoryDTO, CategoryWithoutUUIDDTO.class), false, true);

        return modelMapper
                .map(categoryRepository
                        .save(modelMapper
                                .map(getCategory(categoryDTO.getCategoryCode()),
                                        Category.class)),
                        CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "Category", key = "#categoryCode")
    public CategoryDTO getByCategoryCode(UUID categoryCode) {
        return modelMapper.map(getCategory(categoryCode), CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "Category", key = "#name")
    public CategoryDTO getByCategoryName(String name) {
        return modelMapper
                .map(categoryRepository
                                .findByName(name)
                                .orElseThrow(() -> new CategoryNotFoundException(categoryNotFound)),
                        CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "Category", key = "#categoryID")
    public CategoryDTO getByCategoryID(long categoryID) {
        return modelMapper
                .map(categoryRepository
                                .findById(categoryID).orElseThrow(() -> new CategoryNotFoundException(categoryNotFound)),
                        CategoryDTO.class);
    }

    @Override
    @Cacheable(value = "Category")
    public List<CategoryDTO> getCategoryList() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    @Override
    @CacheEvict(value = "Category", key = "#categoryCode")
    public void deleteCategoryByCategoryCode(UUID categoryCode) {
        categoryRepository
                .deleteByCategoryCode(categoryCode)
                .orElseThrow(() -> new StockNotFoundException(categoryNotFound));
    }

    private CategoryDTO getCategory(UUID categoryCode){
        return modelMapper
                .map(categoryRepository
                                .findByCategoryCode(categoryCode)
                                .orElseThrow(() -> new StockNotFoundException(categoryNotFound)),
                        CategoryDTO.class);
    }
}
