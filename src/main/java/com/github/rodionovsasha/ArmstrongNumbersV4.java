package com.github.rodionovsasha;

import java.util.stream.IntStream;

/**
 * Search and prints all Armstrong Numbers for 'Long.MAX_VALUE'.
 * <a href="https://mathworld.wolfram.com/NarcissisticNumber.html">Armstrong Numbers</a>
 * <p>
 * Execution time: 571ms
 * Used memory: 3mb
 */
class ArmstrongNumbersV4 {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final int AMOUNT_OF_DIGITS_IN_NUMBER = (int) Math.log10(Long.MAX_VALUE) + 1;
    private static final int[] digits1 = new int[AMOUNT_OF_SIMPLE_DIGITS];   // current counts of digits in number during the search
    private static final int[] digits2 = new int[AMOUNT_OF_SIMPLE_DIGITS];   // buffer with counts of digits in number after transformation
    private static final long[] exp = new long[AMOUNT_OF_SIMPLE_DIGITS];     // cached exponents for each digit (exp[d] == d^digitCount)
    private static final int[] maxDigits = new int[AMOUNT_OF_SIMPLE_DIGITS]; // upper bound for overflow checking (invariant digits1[d] <= maxDigits[d])
    private static int digitCount;

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        exp[0] = 0;
        maxDigits[0] = Integer.MAX_VALUE;

        IntStream.range(1, AMOUNT_OF_SIMPLE_DIGITS).forEach(digit -> exp[digit] = 1);

        for (digitCount = 1; digitCount <= AMOUNT_OF_DIGITS_IN_NUMBER; digitCount++) {
            for (int digit = 1; digit < AMOUNT_OF_SIMPLE_DIGITS; digit++) {
                exp[digit] *= digit;
                long m = Long.MAX_VALUE / exp[digit];
                maxDigits[digit] = m < Integer.MAX_VALUE ? (int) m : Integer.MAX_VALUE;
            }
            calc(9, digitCount);
        }

        var executionTime = System.currentTimeMillis() - startTime;

        System.out.printf("Execution time: %dms%n", executionTime);
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    private static void check() {
        var sum = 0L;
        for (int d = 1; d < AMOUNT_OF_SIMPLE_DIGITS; d++) {
            sum += exp[d] * digits1[d];
        }

        System.arraycopy(digits1, 0, digits2, 0, AMOUNT_OF_SIMPLE_DIGITS);

        var s = sum;
        var digCount2 = 0;
        while (s > 0) {
            var a = (int) (s % 10);

            digits2[a]--;
            if (digits2[a] < 0) {
                return;
            }

            digCount2++;
            s /= 10;
        }

        if (digitCount == digCount2) {
            System.out.println(sum); // digits1 are equal to digits2
        }
    }

    private static void calc(int dig, int digCountRest) {
        if (dig == 0) {
            digits1[0] = digCountRest;
            check();
            return;
        }

        int max = Math.min(digCountRest, maxDigits[dig]);  // overflow control

        for (int i = 0; i <= max; i++) {
            digits1[dig] = i;
            calc(dig - 1, digCountRest - i);
        }
    }
}
