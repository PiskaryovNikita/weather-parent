package main.tasks.n3;

import interfaces.task3.StringDiv;
import interfaces.task3.StringUtils;

public class StringUtilsImpl implements StringUtils {

    @Override
    public String compareWords(final String s1, final String s2)
            throws NullPointerException {
        String res = "";
        out: for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
        if (s1.charAt(i) == s2.charAt(j)) {
                    continue out;
                }
            }
            res += s1.charAt(i);
        }
        return res;
    }

    @Override
    public String invert(final String s) throws NullPointerException {
        if (s == null) {
            return "";
        }
        String temp = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            temp += s.charAt(i);
        }
        return temp;
    }

    @Override
    public double parseDouble(final String s)
            throws NullPointerException, IllegalArgumentException {
        int count = 0;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '.') {
                count++;
                if (count > 1) {
                    break;
                }
            } else if (ch == ' ') {
                break;
            }
            res += ch;
        }
        try {
            return Double.parseDouble(res);
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("Invalid input!", exc);
        }
    }

    public static void main(final String[] args) {
        StringUtils utils = new StringUtilsImpl();
        StringDiv stringDiv = new StringDivImpl();
        try {
            // 2
            System.out.println(utils.compareWords("abcd", "cdef"));
            // 3
            System.out.println(utils.invert("abcdef"));
            // 4
            System.out.println(utils.parseDouble("1.23.11"));
            // 1.2563 1234 1.2563d
            // 5
            double res = stringDiv.div("0.0625", "0.125123dgdf");
            System.out.println(res);
        } catch (IllegalArgumentException | NullPointerException exc) {
            exc.printStackTrace(System.err);
            System.out.println("cause: " + exc.getCause());
        } catch (ArithmeticException exc) {
            exc.printStackTrace(System.err);
        }
    }

}
