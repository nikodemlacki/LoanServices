package com.codingExercise.loan.jpa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Loan {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<Investment> investments;
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<Installment> installments;

    public Loan() {}
}
