package com.github.rodionovsasha;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * Search and prints all Apocalyptic Numbers for 1000.
 * <p>
 * Apocalyptic Number - a number of the form 2^n that contains the digits 666 (i.e., the beast number).
 * 2^(157) is an apocalyptic number. The first few such powers are 157, 192, 218, 220, ...
 * <a href="https://mathworld.wolfram.com/ApocalypticNumber.html">Apocalyptic Numbers</a>
 * <p>
 * Execution time: ~1 seconds.
 */
class ApocalypticNumbers {
    private static final int MAX_POWER = 1000;
    private static int counter = 1;

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        IntStream.range(1, MAX_POWER).forEach(i -> {
            var value = BigInteger.TWO.pow(i).toString();
            if (isApocalypticNumber(value)) {
                System.out.printf("%d. Power :: %d, value :: %s%n", counter++, i, value);
            }
        });

        System.out.printf("Execution time: %ds%n", (System.currentTimeMillis() - startTime) / 1000);
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    private static boolean isApocalypticNumber(String number) {
        return number.contains("666");
    }
}
