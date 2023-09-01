package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.stockservice.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CategoryPropertySetter {
    public void setSomeProperties(CategoryDTO categoryDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert && !isCategoryPresent) {
            categoryDTO.setAddDate(LocalDateTime.now());
            categoryDTO.setCategoryCode(UUID.randomUUID());
        }

        categoryDTO.setUpdateDate(LocalDateTime.now());
    }

    public void setSomeProperties(Category category, CategoryDTO categoryDTO){
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryCode(category.getCategoryCode());
        categoryDTO.setAddDate(category.getAddDate());
    }
}
