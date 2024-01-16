package com.github.rodionovsasha;

import java.util.stream.IntStream;

/**
 * Check Happy Number.
 * <p>
 * A Happy Number is a positive integer that, which is replaced by the sum of the squares of its digits, eventually reaching 1.
 * <p>Let's take an example to better understand Happy Numbers. Consider the number 19.
 * To determine if it's a Happy Number, you will calculate the final sum of the squares of its digits: 1^2 + 9^2 = 1 + 81 = 82.
 * You will need to repeat the process with 82: 8^2 + 2^2 = 64 + 4 = 68.
 * Continuing with 68: 6^2 + 8^2 = 36 + 64 = 100.
 * Finally, with 100: 1^2 + 0^2 + 0^2 = 1 + 0 + 0 = 1.
 * As the process leads to 1, it can be concluded that 19 is indeed a Happy Number.
 * <p>
 * <a href="https://mathworld.wolfram.com/HappyNumber.html">HappyNumber</a>
 */
public class CheckHappyNumber {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final long[] ARRAY_OF_SQUARES = new long[AMOUNT_OF_SIMPLE_DIGITS];

    static {
        IntStream.range(0, AMOUNT_OF_SIMPLE_DIGITS).forEach(i -> ARRAY_OF_SQUARES[i] = (long) i * i);
    }

    public static boolean isHappyNumber(long number) {
        var result = number;

        while (result != 1 && result != 4) {
            result = calculateSum(result);
        }

        return result == 1;
    }

    //Calculates the sum of squares of digits
    private static long calculateSum(long number) {
        long sum = 0;

        while (number > 0) {
            var digit = (int) (number % 10);
            sum += ARRAY_OF_SQUARES[digit];
            number = number / 10;
        }

        return sum;
    }
}
