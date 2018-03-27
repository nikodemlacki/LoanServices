package com.codingExercise.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(UUID loanId) {
        super("Loan with ID '" + loanId + "' not found");
    }
}
