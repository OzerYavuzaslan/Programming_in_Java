package com.ozeryavuzaslan.stockservice.service;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    CategoryDTO getByCategoryName(String name);
    CategoryDTO getByCategoryID(long categoryID);
    List<CategoryDTO> getCategoryList();
    void deleteStockByCategoryName(String name);
}
