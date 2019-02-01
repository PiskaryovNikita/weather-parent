package main.tasks.n2;

import interfaces.task2.FractionNumber;
import interfaces.task2.FractionNumberOperation;

public class FractionNumberOperationImpl implements FractionNumberOperation {

    @Override
    public FractionNumber add(FractionNumber num1, FractionNumber num2) {
        int dividend = num1.getDividend() * num2.getDivisor()
                + num2.getDividend() * num1.getDivisor();
        int divisor = num1.getDivisor() * num2.getDivisor();
        return new FractionNumberImpl(dividend, divisor);
    }

    @Override
    public FractionNumber div(FractionNumber num1, FractionNumber num2) {
        if (num2.value() == 0) {
            throw new ArithmeticException();
        }
        int dividend = num1.getDividend() * num2.getDivisor();
        int divisor = num1.getDivisor() * num2.getDividend();
        return new FractionNumberImpl(dividend, divisor);
    }

    @Override
    public FractionNumber mul(FractionNumber num1, FractionNumber num2) {
        int dividend = num1.getDividend() * num2.getDividend();
        int divisor = num1.getDivisor() * num2.getDivisor();
        return new FractionNumberImpl(dividend, divisor);
    }

    @Override
    public FractionNumber sub(FractionNumber num1, FractionNumber num2) {
        int dividend = num1.getDividend() * num2.getDivisor()
                - num2.getDividend() * num1.getDivisor();
        int divisor = num1.getDivisor() * num2.getDivisor();
        return new FractionNumberImpl(dividend, divisor);
    }

}
