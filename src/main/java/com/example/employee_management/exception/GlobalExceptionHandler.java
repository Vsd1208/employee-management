package com.example.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoEmployeesFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoEmployeesFound(NoEmployeesFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("No employees found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Employee not found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDepartmentFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoDepartmentFound(NoDepartmentFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("No department found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
