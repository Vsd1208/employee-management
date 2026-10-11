
package com.example.employee_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_management.dto.DepartmentRequest;
import com.example.employee_management.dto.DepartmentResponse;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.DepartmentNotFoundException;
import com.example.employee_management.exception.NoDepartmentFoundException;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
    }

    private EmployeeResponse toEmployeeResponse(Employee employee) {
    return new EmployeeResponse(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment().getName(),
            employee.getSalary());
}
    private Department toDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        return department;
    }

    private DepartmentResponse toDepartmentResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName());
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentByID(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoDepartmentFoundException(
                        "Department not found with id: " + id));

        return toDepartmentResponse(department);
    }

    public List<DepartmentResponse> getDepartmentsByName(String name) {
        List<Department> departments = departmentRepository.findByNameContainingIgnoreCase(name);

        if (departments.isEmpty()) {
            throw new DepartmentNotFoundException(
                    "Department not found with name: " + name);
        }

        return departments.stream()
                .map(this::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = toDepartment(request);
        Department savedDepartment = departmentRepository.save(department);

        return toDepartmentResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(
            Long id, DepartmentRequest updatedDepartment) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new NoDepartmentFoundException(
                        "Department not found with id: " + id));

        existingDepartment.setName(updatedDepartment.getName());

        Department savedDepartment = departmentRepository.save(existingDepartment);

        return toDepartmentResponse(savedDepartment);
    }

    public void deleteDepartmentById(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NoDepartmentFoundException(
                    "Department not found with id: " + id);
        }

        departmentRepository.deleteById(id);
    }

    public Department getDepartmentEntityByID(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NoDepartmentFoundException(
                        "Department not found with id: " + id));
    }
    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        getDepartmentEntityByID(departmentId);
        List<Employee> employees =  employeeRepository.findByDepartmentId(departmentId);
    return employees.stream().map(this::toEmployeeResponse).toList();
}
}
