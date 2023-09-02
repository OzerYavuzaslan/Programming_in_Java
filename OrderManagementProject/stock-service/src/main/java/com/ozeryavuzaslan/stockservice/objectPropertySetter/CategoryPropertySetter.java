package com.ozeryavuzaslan.stockservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CategoryPropertySetter {
    public void setSomeProperties(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert && !isCategoryPresent) {
            categoryWithoutUUIDDTO.setAddDate(LocalDateTime.now());
            categoryWithoutUUIDDTO.setCategoryCode(UUID.randomUUID());
        }

        categoryWithoutUUIDDTO.setUpdateDate(LocalDateTime.now());
    }

    public void setSomeProperties(Category category, CategoryWithoutUUIDDTO categoryWithoutUUIDDTO){
        categoryWithoutUUIDDTO.setId(category.getId());
        categoryWithoutUUIDDTO.setCategoryCode(category.getCategoryCode());
        categoryWithoutUUIDDTO.setAddDate(category.getAddDate());
    }
}
