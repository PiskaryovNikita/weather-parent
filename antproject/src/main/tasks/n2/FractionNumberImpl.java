package main.tasks.n2;

import interfaces.task2.FractionNumber;

public class FractionNumberImpl implements FractionNumber {
    private int dividend;
    private int divisor;
    private double value;

    public FractionNumberImpl() {

    }

    public FractionNumberImpl(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Division by zero!");
        }
        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public int getDividend() {
        return dividend;
    }

    @Override
    public int getDivisor() {
        return divisor;
    }

    @Override
    public void setDividend(int dividend) {
        this.dividend = dividend;
    }

    @Override
    public void setDivisor(int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException();
        }
        this.divisor = divisor;
    }

    @Override
    public String toStringValue() {
        return dividend + " / " + divisor;
    }

    @Override
    public double value() {
        value = (double) dividend / divisor;
        return value;
    }

    @Override
    public String toString() {
        return dividend + "/" + divisor;
    }
@Override
    public boolean equals(Object obj) {
        FractionNumber fn = (FractionNumber) obj;
        return value() == fn.value();
    }

    public static void main(String[] args) {
        FractionNumberImpl fnum = new FractionNumberImpl(1, 4);
        System.out.println(fnum.value());
        System.out.println(fnum.toStringValue());
    }

}
