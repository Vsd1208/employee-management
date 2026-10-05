package com.example.employee_management.exception;

public class NoDepartmentFoundException extends RuntimeException {
    public NoDepartmentFoundException(String message) {
        super(message);
    }
}
