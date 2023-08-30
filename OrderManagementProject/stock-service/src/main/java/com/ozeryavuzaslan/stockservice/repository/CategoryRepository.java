package com.ozeryavuzaslan.stockservice.repository;

import com.ozeryavuzaslan.stockservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
