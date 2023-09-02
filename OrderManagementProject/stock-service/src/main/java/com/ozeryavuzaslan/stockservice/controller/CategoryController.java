package com.ozeryavuzaslan.stockservice.controller;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.service.CategoryService;
import com.ozeryavuzaslan.stockservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {
    private final CategoryService categoryService;
    private final CustomLocation customLocation;

    @Value("${category.get.endpoint}")
    private String categoryGetEndpoint;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> insertCategory(@Valid @RequestBody CategoryWithoutUUIDDTO categoryWithoutUUIDDTO){
        return ResponseEntity.created(customLocation.getURILocation(categoryGetEndpoint,
                        categoryService
                                .saveCategory(categoryWithoutUUIDDTO)
                                .getId()))
                .build();
    }

    @PutMapping("/categories")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO));
    }

    @GetMapping("/categories/getByCategoryCode/{uuid}")
    public ResponseEntity<CategoryDTO> getByCategoryID(@PathVariable UUID uuid){
        return ResponseEntity.ok(categoryService.getByCategoryCode(uuid));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getByCategoryID(@PathVariable long id){
        return ResponseEntity.ok(categoryService.getByCategoryID(id));
    }

    @GetMapping("/categories/getByCategoryName/{name}")
    public ResponseEntity<CategoryDTO> getByCategoryID(@PathVariable String name){
        return ResponseEntity.ok(categoryService.getByCategoryName(name));
    }

    @GetMapping("/categories/getCategoryList")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @DeleteMapping("/categories")
    public ResponseEntity<String> deleteStock(@RequestParam UUID categoryCode){
        categoryService.deleteCategoryByCategoryCode(categoryCode);
        return new ResponseEntity<>(categoryCode + " has been deleted.", HttpStatus.NO_CONTENT);
    }
}
