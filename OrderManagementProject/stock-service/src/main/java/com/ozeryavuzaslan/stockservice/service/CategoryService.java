package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    CategoryDTO getByCategoryCode(UUID categoryCode);
    CategoryDTO getByCategoryName(String name);
    CategoryDTO getByCategoryID(long categoryID);
    List<CategoryDTO> getCategoryList();
    void deleteStockByCategoryName(UUID categoryCode);
}
