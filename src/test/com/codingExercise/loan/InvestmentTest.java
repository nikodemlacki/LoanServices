package com.codingExercise.loan;

import com.codingExercise.loan.jpa.Investment;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class InvestmentTest extends TestCase {
    private final static BigDecimal INTEREST_RATE = new BigDecimal(0.05);
    private Investment investment;

    protected void setUp() {
        this.investment = new Investment();
        this.investment.setAmount(new BigDecimal(1000));
    }

    public void testCalculate() {
        BigDecimal interest = this.investment.calculateInterest(INTEREST_RATE);
        assertEquals(BigDecimal.ZERO.setScale(2), interest);

        this.investment.setDaysAfterFirstAdvance(31);
        interest = this.investment.calculateInterest(INTEREST_RATE);
        assertEquals(new BigDecimal(4.00).setScale(2), interest);
    }
}
