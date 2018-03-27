package com.codingExercise.loan.jpa;

import com.codingExercise.loan.calculator.APRCalculator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Investment {
    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal amount;
    private int daysAfterFirstAdvance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Installment> installments;

    public Investment() {super();}

    public Investment(BigDecimal amount) {
        super();
        this.amount = amount;
    }

    public BigDecimal calculateInterest(BigDecimal apr) {
        APRCalculator calc = new APRCalculator(this.amount);

        return calc.calculateSinglePayment(apr, this.daysAfterFirstAdvance);
    }
}
