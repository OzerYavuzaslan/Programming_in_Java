package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    CategoryDTO getByCategoryCode(UUID categoryCode);
    CategoryDTO getByCategoryName(String name);
    CategoryDTO getByCategoryID(long categoryID);
    List<CategoryDTO> getCategoryList();
    void deleteCategoryByCategoryCode(UUID categoryCode);
}
