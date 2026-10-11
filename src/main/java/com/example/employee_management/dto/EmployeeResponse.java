package com.example.employee_management.dto;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String departmentName;
    private double salary;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Long id, String name, String email, String departmentName, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
