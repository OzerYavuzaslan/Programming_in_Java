package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.stockservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByCategoryCode(UUID categoryCode);
    Optional<Category> deleteByCategoryCode(UUID categoryCode);
}
