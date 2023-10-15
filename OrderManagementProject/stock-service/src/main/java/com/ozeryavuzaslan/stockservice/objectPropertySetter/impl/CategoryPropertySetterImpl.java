package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoryPropertySetterImpl implements CategoryPropertySetter {
    @Override
    public void setSomeProperties(CategoryWithoutUUIDDTO categoryWithoutUUIDDTO, boolean isInsert, boolean isCategoryPresent){
        if (isInsert && !isCategoryPresent) {
            categoryWithoutUUIDDTO.setAddDate(LocalDateTime.now());
            categoryWithoutUUIDDTO.setCategoryCode(UUID.randomUUID());
        }

        categoryWithoutUUIDDTO.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void setSomeProperties(Category category, CategoryWithoutUUIDDTO categoryWithoutUUIDDTO){
        categoryWithoutUUIDDTO.setId(category.getId());
        categoryWithoutUUIDDTO.setCategoryCode(category.getCategoryCode());
        categoryWithoutUUIDDTO.setAddDate(category.getAddDate());
    }
}
