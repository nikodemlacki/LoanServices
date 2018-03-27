package com.codingExercise.loan.jpa;

import com.codingExercise.loan.calculator.APRCalculator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Installment {
    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal amount;
    private int daysAfterFirstInstallment;
}
