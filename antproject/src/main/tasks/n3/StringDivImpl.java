package main.tasks.n3;

import interfaces.task3.StringDiv;

public class StringDivImpl implements StringDiv {

    @Override
    public double div(String s1, String s2) throws NullPointerException,
            IllegalArgumentException, ArithmeticException {
        double dividend, divisor;
        try {
         dividend = Double.parseDouble(s1); 
         divisor = Double.parseDouble(s2);
        }catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("qwe", exc);
        }
         if (divisor == 0) {
            throw new ArithmeticException("qwe");
        }
         return dividend / divisor;
    }

}
