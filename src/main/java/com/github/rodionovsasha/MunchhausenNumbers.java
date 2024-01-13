package com.github.rodionovsasha;

import java.util.stream.IntStream;

/**
 * Search and prints all Munchhausen Numbers for 'Integer.MAX_VALUE'.
 * <a href="https://mathworld.wolfram.com/MuenchhausenNumber.html">Munchhausen Numbers</a>
 * <p>
 * Note: if the definition 0^0 = 0 is adopted, then there are exactly four Munchhausen Numbers: 0, 1, 3435, and 438579088,
 * otherwise only 0, 1, 3435.
 * <p>
 * Execution time: ~30 seconds.
 */
class MunchhausenNumbers {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final int MAX_NUMBER = Integer.MAX_VALUE;
    private static final int[] ARRAY_OF_POWERS = new int[AMOUNT_OF_SIMPLE_DIGITS];

    static {
        IntStream.range(0, AMOUNT_OF_SIMPLE_DIGITS).forEach(i -> ARRAY_OF_POWERS[i] = i == 0 ? 0 : (int) Math.pow(i, i));
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        IntStream.iterate(0, i -> i < MAX_NUMBER, i -> i + 1)
                .filter(MunchhausenNumbers::isMunchhausenNumber)
                .forEach(System.out::println);

        System.out.printf("Execution time: %ds%n", (System.currentTimeMillis() - startTime) / 1000);
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    private static boolean isMunchhausenNumber(int number) {
        var sum = 0L;
        var temp = number;

        while (temp > 0) {
            var digit = temp % 10; //yields the rightest digit as remainder
            sum = sum + ARRAY_OF_POWERS[digit];
            temp /= 10;            // yields the remaining number
        }

        return sum == number;      // returns true if sum is equal to original number
    }
}
