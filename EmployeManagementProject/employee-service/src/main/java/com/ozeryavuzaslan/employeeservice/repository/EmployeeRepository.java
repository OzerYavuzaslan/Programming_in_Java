package com.ozeryavuzaslan.employeeservice.repository;

import com.ozeryavuzaslan.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String eMail);
    void deleteByEmail(String eMail);
}
