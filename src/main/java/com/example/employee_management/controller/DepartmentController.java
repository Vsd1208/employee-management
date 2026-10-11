
package com.example.employee_management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.employee_management.dto.DepartmentRequest;
import com.example.employee_management.dto.DepartmentResponse;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentByID(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<DepartmentResponse>> searchDepartmentsByName(
            @PathVariable String name) {

        return ResponseEntity.ok(
                departmentService.getDepartmentsByName(name));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest department) {

        DepartmentResponse savedDepartment = departmentService.createDepartment(department);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest updatedDepartment) {

        DepartmentResponse department = departmentService.updateDepartment(id, updatedDepartment);

        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartmentById(
            @PathVariable Long id) {

        departmentService.deleteDepartmentById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getDepartments() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
            departmentService.getEmployeesByDepartment(departmentId));
    }
}