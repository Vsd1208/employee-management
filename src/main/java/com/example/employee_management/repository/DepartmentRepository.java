package com.example.employee_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee_management.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Additional query methods can be defined here if needed
    List<Department> findByNameContainingIgnoreCase(String name);
}
