package com.github.vmyasnik.fastREST.utils;

import org.junit.Assert;

import java.math.BigDecimal;

public class CompareHelper {
    public static void compareObjects(String variable, String expected, Object actual, String operator) {
        Assert.assertTrue("Переменная: " + variable +
                        " Ожидаемое: " + expected +
                        " Фактическое: " + actual +
                        " Оператор: " + operator,
                CompareHelper.compareOp(actual, operator, expected));
    }

    private static boolean compareOp(Object temp, String operator, String s1) {
        System.out.println("comparing: " + temp + " " + operator + " " + s1);
        switch (operator) {
            case "eq":
            case "==": {
                return compareMixedNumberTypes(temp, s1) == 0;
            }
            case "neq":
            case "!=":
                return compareMixedNumberTypes(temp, s1) != 0;
            case ">": {
                return compareMixedNumberTypes(temp, s1) == 1;
            }
            case "<": {
                return compareMixedNumberTypes(temp, s1) == -1;
            }
            case ">=": {
                return compareMixedNumberTypes(temp, s1) >= 0;
            }
            case "<=": {
                return compareMixedNumberTypes(temp, s1) <= 0;
            }
            case "contains": {
                return temp.toString().contains(s1);
            }
            case "!contains": {
                return !temp.toString().contains(s1);
            }
            default: {
                throw new AssertionError("No compare options");
            }
        }
    }

    public static int compareMixedNumberTypes(Object n1, Object n2) {
        try {
            if (n1 == null) {
                n1 = "null";
            }
            if (n2 == null) {
                n2 = "null";
            }
            BigDecimal b1 = new BigDecimal(n1.toString());
            BigDecimal b2 = new BigDecimal(n2.toString());
            return b1.compareTo(b2);
        } catch (NumberFormatException nfe) {
            return n1.toString().equals(n2.toString()) ? 0 : -2;
        }

    }
}

