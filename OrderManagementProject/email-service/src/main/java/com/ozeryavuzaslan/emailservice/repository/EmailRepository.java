package com.ozeryavuzaslan.emailservice.repository;

import com.ozeryavuzaslan.emailservice.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
