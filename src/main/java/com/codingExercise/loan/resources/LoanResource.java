package com.codingExercise.loan.resources;

import com.codingExercise.loan.jpa.Loan;
import com.codingExercise.loan.controller.LoanController;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Getter
public class LoanResource extends ResourceSupport {
    private final Loan loan;

    public LoanResource(Loan loan) {
        this.loan = loan;
        this.add(new Link(loan.getId().toString(), "loan-id"));
        this.add(linkTo(methodOn(LoanController.class).getAllLoans()).withSelfRel());
    }
}
