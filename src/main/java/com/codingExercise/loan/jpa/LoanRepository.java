package com.codingExercise.loan.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findById(UUID id);
}