package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.stockservice.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryPropertySetter {
    public CategoryDTO setDates(CategoryDTO categoryDTO, boolean isInsert){
        if (isInsert)
            categoryDTO.setAddDate(LocalDateTime.now());

        categoryDTO.setUpdateDate(LocalDateTime.now());

        return categoryDTO;
    }

    public Category setDates(Category category){
        category.setUpdateDate(LocalDateTime.now());
        return category;
    }
}
