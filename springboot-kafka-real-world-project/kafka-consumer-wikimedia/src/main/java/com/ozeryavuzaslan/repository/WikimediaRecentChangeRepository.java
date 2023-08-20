package com.ozeryavuzaslan.repository;

import com.ozeryavuzaslan.model.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaRecentChangeRepository extends JpaRepository<WikimediaData, String> {
}
