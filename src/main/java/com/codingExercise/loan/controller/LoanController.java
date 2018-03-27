package com.codingExercise.loan.controller;

import com.codingExercise.loan.exception.LoanNotFoundException;
import com.codingExercise.loan.jpa.Loan;
import com.codingExercise.loan.jpa.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanRepository loanRepository;


    @Autowired
    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json", "application/vnd.com.codingExercise.loan.v1+json;qs=1"})
    public Collection<Loan> getAllLoans() {

        // This will have to be limited in future versions; this method may be removed altogether or pagination can be
        // added to manage the number of results returned to client
        return this.loanRepository.findAll();
    }

    @RequestMapping(
            value = "/{loanId}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/vnd.com.codingExercise.loan.v1+json;qs=1"})
    public ResponseEntity<Loan> getLoan(@PathVariable UUID loanId) {

        Optional<Loan> loan = this.loanRepository.findById(loanId);

        return new ResponseEntity<>(loan.orElseThrow(() -> new LoanNotFoundException(loanId)), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/json", "application/vnd.com.codingExercise.loan.v1+json;qs=1"})
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {

        loan = this.loanRepository.save(loan);

        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{loanId}",
            method = RequestMethod.POST,
            produces = {"application/json", "application/vnd.com.codingExercise.loan.v1+json;qs=1"})
    public void createLoan(@PathVariable UUID loanId) {
        Loan loan = new Loan();
        loan.setId(loanId);
        this.loanRepository.delete(loan);
    }
}
