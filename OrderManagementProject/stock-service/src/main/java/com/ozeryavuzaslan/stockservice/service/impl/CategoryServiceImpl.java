package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.CategoryPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryPropertySetter categoryPropertySetter;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        categoryPropertySetter.setSomeProperties(categoryDTO, true, false);

        return modelMapper
                .map(categoryRepository
                        .save(modelMapper
                                .map(categoryDTO, Category.class)),
                        CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        categoryPropertySetter.setSomeProperties(categoryDTO, false, true);

        return modelMapper
                .map(categoryRepository
                        .save(modelMapper
                                .map(categoryDTO, Category.class)),
                        CategoryDTO.class);
    }

    @Override
    public CategoryDTO getByCategoryCode(UUID categoryCode) {
        return modelMapper.map(getCategory(categoryCode), CategoryDTO.class);
    }

    @Override
    public CategoryDTO getByCategoryName(String name) {
        return null;
    }

    @Override
    public CategoryDTO getByCategoryID(long categoryID) {
        return null;
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        return null;
    }

    @Override
    public void deleteStockByCategoryName(UUID categoryCode) {

    }

    private CategoryDTO getCategory(UUID productCode){
        return modelMapper
                .map(categoryRepository
                                .findByCategoryCode(productCode)
                                .orElseThrow(() -> new StockNotFoundException(CATEGORY_NOT_FOUND)),
                        CategoryDTO.class);
    }
}
