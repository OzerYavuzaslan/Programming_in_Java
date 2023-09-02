package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Category;

public interface CategoryPropertySetter {
    void setSomeProperties(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent);
    void setSomeProperties(Category category, CategoryWithoutUUIDDTO categoryWithoutUUIDDTO);
}
