package com.example.employee_management.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {
    @NotBlank
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
