package com.github.rodionovsasha;

import java.math.BigInteger;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

public class ArmstrongNumbersAllV2 {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final int MAX_DIGITS_AMOUNT = 39;       // max digits amount where armstrong numbers exist(39)
    private static final BigInteger MAX_NUMBER = new BigInteger(repeat("9", MAX_DIGITS_AMOUNT));

    private static BigInteger[] currentCountOfDigits = new BigInteger[AMOUNT_OF_SIMPLE_DIGITS]; // current counts of digits in number during the search
    private static BigInteger[] currentCountOfDigitsAfterTransformation = new BigInteger[AMOUNT_OF_SIMPLE_DIGITS]; // buffer with counts of digits in number after transformation
    private static BigInteger[] cachedExponents = new BigInteger[AMOUNT_OF_SIMPLE_DIGITS]; // cached exponents for each digit (exp[d] == d^digitCount)
    private static BigInteger[] maxDigits = new BigInteger[AMOUNT_OF_SIMPLE_DIGITS];       // upper bound for overflow checking (invariant digits1[d] <= maxDigits[d])

    private static BigInteger digitCount;
    private static int counter = 1;

    private static void check() {
        BigInteger sum = ZERO;
        for (int d = 1; d < AMOUNT_OF_SIMPLE_DIGITS; d++) {
            sum = sum.add(cachedExponents[d].multiply(currentCountOfDigits[d]));
        }

        System.arraycopy(currentCountOfDigits, 0, currentCountOfDigitsAfterTransformation, 0, AMOUNT_OF_SIMPLE_DIGITS);

        BigInteger s = sum;
        BigInteger digCount2 = ZERO;
        while (s.compareTo(ZERO) > 0) {
            int a = s.mod(TEN).intValue();

            currentCountOfDigitsAfterTransformation[a] = currentCountOfDigitsAfterTransformation[a].subtract(ONE);
            if (currentCountOfDigitsAfterTransformation[a].compareTo(ZERO) < 0) {
                return;
            }

            digCount2 = digCount2.add(ONE);
            s = s.divide(TEN);
        }

        if (digitCount.compareTo(digCount2) == 0) {
            System.out.println(counter++ + ". " + sum); // digitCount is equal to digitCount2
        }
    }

    private static void calc(int dig, BigInteger digCountRest) {
        if (dig == 0) {
            currentCountOfDigits[0] = digCountRest;
            check();
            return;
        }

        BigInteger max = digCountRest.compareTo(maxDigits[dig]) < 0 ? digCountRest : maxDigits[dig];  // overflow control

        for (BigInteger i = ZERO; i.compareTo(max) <= 0; i = i.add(ONE)) {
            currentCountOfDigits[dig] = i;
            calc(dig - 1, digCountRest.subtract(i));
        }
    }

    private static void getNumbers() {
        cachedExponents[0] = ZERO;
        maxDigits[0] = valueOf(Integer.MAX_VALUE);

        IntStream.range(1, AMOUNT_OF_SIMPLE_DIGITS).forEach(
                digit -> cachedExponents[digit] = ONE
        );

        for (digitCount = ONE; digitCount.compareTo(valueOf(MAX_DIGITS_AMOUNT)) <= 0; digitCount = digitCount.add(ONE)) {
            for (BigInteger digit = ONE; digit.compareTo(valueOf(AMOUNT_OF_SIMPLE_DIGITS)) < 0; digit = digit.add(ONE)) {
                cachedExponents[digit.intValue()] = cachedExponents[digit.intValue()].multiply(digit);
                BigInteger m = MAX_NUMBER.divide(cachedExponents[digit.intValue()]);
                maxDigits[digit.intValue()] = m.compareTo(valueOf(Integer.MAX_VALUE)) < 0 ? m : valueOf(Integer.MAX_VALUE);
            }
            calc(9, digitCount);
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        getNumbers();

        long executionTime = System.currentTimeMillis() - startTime;
        long minutes = (executionTime / 1000) / 60;
        long seconds = (executionTime / 1000) % 60;

        System.out.printf("Execution time: %dm%ds%n", minutes, seconds);
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    /**
     * Repeat string <b>str</b> <b>times</b> time.
     *
     * @param str   string to repeat
     * @param times repeat str times time
     * @return generated string
     */
    private static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
}
