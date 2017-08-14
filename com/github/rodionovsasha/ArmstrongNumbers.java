package com.github.rodionovsasha;

import java.util.Set;
import java.util.TreeSet;

public class ArmstrongNumbers {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final long MAX_NUMBER = Long.MAX_VALUE;
    private static final int AMOUNT_OF_DIGITS_IN_NUMBER = (int)Math.log10(MAX_NUMBER) + 1;
    private static final long[][] ARRAY_OF_POWERS = new long[AMOUNT_OF_SIMPLE_DIGITS][AMOUNT_OF_DIGITS_IN_NUMBER + 1];

    static {
        for (int i = 0; i < AMOUNT_OF_SIMPLE_DIGITS; i++) {
            for (int j = 1; j <= AMOUNT_OF_DIGITS_IN_NUMBER; j++) {
                ARRAY_OF_POWERS[i][j] = pow(i, j);
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Set<Long> result = getNumbers(MAX_NUMBER);

        int i = 1;
        for (long armstrongNumber : result) {
            System.out.println(i + ". " + armstrongNumber);
            i++;
        }
        System.out.println(String.format("Execution time: %dms", (System.currentTimeMillis() - startTime)));
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    private static Set<Long> getNumbers(final long number) {
        Set<Long> armstrongNumbers = new TreeSet<>();

        //Main loop
        for (long i = 1; i < number; i = getNextNumber(i)) {
            if (i < 0) {
                break; // the maximum value is reached
            }

            long sumOfPowers = getSumOfPowers(i);
            if (sumOfPowers <= number && isArmstrongNumber(sumOfPowers)) {
                armstrongNumbers.add(sumOfPowers);
            }
        }

        return armstrongNumbers;
    }

    private static long getNextNumber(final long number) {
        long copyOfNumber = number;
        if (isGrowingNumber(copyOfNumber)) { // here we have numbers where each digit not less than previous one and not more than next one: 12, 1557, 333 and so on.
            return ++copyOfNumber;
        } else { // here we have numbers which end in zero: 10, 20, ..., 100, 110, 5000, 1000000 and so on.
            long count = 1;

            while (copyOfNumber % 10 == 0) {// 5000 -> 500 -> 50: try to get the last non-zero digit
                copyOfNumber = copyOfNumber / 10;
                count = count * 10;
            }
            long lastNonZeroDigit = copyOfNumber % 10;

            count = lastNonZeroDigit * count / 10;
            return number + count;
        }
    }

    /**
     * Analog of Math.pow which works with long type
     */
    private static long pow(final int base, final int exponent) {
        long pow = 1;
        for (int i = 1; i <= exponent; i++) {
            pow *= base;
        }
        return pow;
    }

    /*
    * 135 returns true:  1 < 3 < 5
    * 153 returns false: 1 < 5 > 3
    * */
    private static boolean isGrowingNumber(long number) {
        return (number + 1) % 10 != 1;
    }

    private static long getSumOfPowers(long number) {
        int power = (int)Math.log10(number) + 1; // get amount of digits in a number
        long currentSum = 0;
        while (number > 0) {
            currentSum = currentSum + ARRAY_OF_POWERS[(int)(number % 10)][power]; // get powers from array by indexes and then the sum.
            number = number / 10;
        }
        return currentSum;
    }

    private static boolean isArmstrongNumber(final long number) {
        return number == getSumOfPowers(number);
    }
}