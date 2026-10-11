package com.example.employee_management.service;

import com.example.employee_management.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.exception.NoEmployeesFoundException;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    private EmployeeResponse toEmployeeResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getName(),
                employee.getSalary());
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        return toEmployeeResponse(employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id)));
    }

    public void deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        } else {
            employeeRepository.deleteById(id);
        }
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Department department = departmentService.getDepartmentEntityByID(request.getDepartmentId());
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        return toEmployeeResponse(savedEmployee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest updatedRequest) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        existingEmployee.setName(updatedRequest.getName());
        existingEmployee.setEmail(updatedRequest.getEmail());
        existingEmployee.setDepartment(departmentService.getDepartmentEntityByID(updatedRequest.getDepartmentId()));
        existingEmployee.setSalary(updatedRequest.getSalary());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return toEmployeeResponse(updatedEmployee);
    }

    public List<EmployeeResponse> searchEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.findByNameContainingIgnoreCase(name);
        if (employees.isEmpty()) {
            throw new NoEmployeesFoundException("No employees found with name: " + name);
        }
        return employees.stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        if (employees.isEmpty()) {
            throw new NoEmployeesFoundException("No employees found in department with id: " + departmentId);
        }
        return employees.stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }
}
