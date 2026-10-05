package com.example.employee_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_management.exception.DepartmentNotFoundException;
import com.example.employee_management.exception.NoDepartmentFoundException;
import com.example.employee_management.entity.Department;
import com.example.employee_management.repository.DepartmentRepository;

@Service
public class DepartmentService {

    DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentByID(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NoDepartmentFoundException(
                        "Department not found with id: " + id));
    }

    public List<Department> getDepartmentsByName(String name) {
        List<Department> departments = departmentRepository.findByNameContainingIgnoreCase(name);

        if (departments.isEmpty()) {
            throw new DepartmentNotFoundException(
                    "Department not found with name: " + name);
        }
        return departments;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new NoDepartmentFoundException(
                    "Department not found with id: " + id);
        }

        departmentRepository.deleteById(id);
    }

    public void updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new NoDepartmentFoundException("Department not found with id: " + id));
        existingDepartment.setName(updatedDepartment.getName());
        departmentRepository.save(existingDepartment);
    }
}