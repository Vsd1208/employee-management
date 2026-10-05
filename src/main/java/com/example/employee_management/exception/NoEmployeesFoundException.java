package com.example.employee_management.exception;

public class NoEmployeesFoundException extends RuntimeException {
    public NoEmployeesFoundException(String message) {
        super(message);
    }
}
