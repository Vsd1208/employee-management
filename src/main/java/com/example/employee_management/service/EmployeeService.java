package com.example.employee_management.service;

import com.example.employee_management.repository.EmployeeRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.exception.NoEmployeesFoundException;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public void deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        } else {
            employeeRepository.deleteById(id);
        }
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (employeeRepository.existsById(id)) {
            Employee existingEmployee = employeeRepository.findById(id).get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }

    public List<Employee> searchEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.findByNameContainingIgnoreCase(name);
        if (employees.isEmpty()) {
            throw new NoEmployeesFoundException("No employees found with name: " + name);
        }
        return employees;
    }
}
