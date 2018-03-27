package com.codingExercise.loan.controller;

import com.codingExercise.loan.jpa.Investment;
import com.codingExercise.loan.jpa.Loan;
import com.codingExercise.loan.jpa.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/interest/{loanId}")
public class InterestController {
    private LoanRepository loanRepository;

    @Autowired
    public InterestController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @RequestMapping("/interest")
    public ResponseEntity<Investment> calculateInterest(@PathVariable(name="loanId") UUID loanId) {
        Optional<Loan> loan = this.loanRepository.findById(loanId);

        return null;
    }
}
