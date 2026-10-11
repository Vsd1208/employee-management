package com.example.employee_management.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ApiErrorResponse errorResponse = new ApiErrorResponse("Validation error", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
