package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.stockservice.service.CategoryService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ozeryavuzaslan.basedomains.util.Constants.CATEGORY_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {
    private final CategoryService categoryService;
    private final CustomLocation customLocation;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.created(customLocation.getURILocation(CATEGORY_ENDPOINT,
                        categoryService
                                .saveCategory(categoryDTO)
                                .getId()))
                .build();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getByCategoryID(@PathVariable long id){
        return ResponseEntity.ok(categoryService.getByCategoryID(id));
    }
}
