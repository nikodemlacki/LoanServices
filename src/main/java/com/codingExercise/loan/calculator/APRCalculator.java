package com.codingExercise.loan.calculator;

import com.codingExercise.loan.jpa.Installment;
import com.codingExercise.loan.jpa.Investment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

public class APRCalculator {
    private Collection<Investment> investments;
    private Collection<Installment> installments;

    public APRCalculator(BigDecimal firstAdvance) {
        this.investments = new ArrayList<>();
        this.investments.add(new Investment(firstAdvance));

        this.installments = new ArrayList<>();
    }

    /**
     *
     * @param apr Loan percentage expressed as a decimal value: 5% should be stated as 0.05
     * @param daysToInstallment Integer number of days from beginning of the loan to the final installment
     * @return The amount of interest to be paid
     */
    public BigDecimal calculateSinglePayment(BigDecimal apr, int daysToInstallment) {
        Investment investment = this.investments.stream().findFirst().get();
        int years = this.daysToYears(daysToInstallment);
        int remainingDaysInLastYear =
                new BigDecimal(daysToInstallment).remainder(new BigDecimal(362.25)).intValue();

        if(years > 0)
            return this.calculateSubPeriod(investment, apr, years)
                .add(this.calculateSubPeriod(investment, apr, 1).multiply(new BigDecimal(remainingDaysInLastYear).divide(new BigDecimal(365.25), 2, RoundingMode.HALF_UP)))
                    .setScale(2, RoundingMode.HALF_UP);
        else
            return this.calculateSubPeriod(investment, apr, 1).multiply(new BigDecimal(remainingDaysInLastYear).divide(new BigDecimal(365.25), 2, RoundingMode.HALF_UP))
                    .setScale(2, RoundingMode.HALF_UP);


    }

    private BigDecimal calculateSubPeriod(Investment investment, BigDecimal apr, int years) {
        return investment.getAmount()
                .multiply(BigDecimal.ONE.add(apr).pow(years))
                .subtract(investment.getAmount());
    }

    private int daysToYears(int daysAfterFirstAdvance) {
        return new BigDecimal(daysAfterFirstAdvance).divide(new BigDecimal(365.25), 2, RoundingMode.HALF_UP).intValue();
    }
}
