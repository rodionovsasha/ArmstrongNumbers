package com.github.rodionovsasha;

import java.util.stream.IntStream;

/**
 * Search and prints all Zero Free Numbers for limit 1_000_000.
 * <p>
 * An integer whose decimal digits contain no zeros is said to be zero-free.
 * The first few positive zero-free integers are 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, ...
 * <a href="https://mathworld.wolfram.com/Zerofree.html">Zerofree</a>
 * <p>
 * Execution time: 1s
 * Used memory: 76mb
 */
class ZeroFreeNumbers {
    private static final int MAX_NUMBER = 1_000_000;

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        IntStream.range(0, MAX_NUMBER).forEach(i -> {
            if (!containsZero(i)) {
                System.out.printf("%s%n", i);
            }
        });

        System.out.printf("Execution time: %ds%n", (System.currentTimeMillis() - startTime) / 1000);
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    private static boolean containsZero(int num) {
        if (num == 0) {
            return true;
        }

        if (num < 0) {
            num = -num;
        }

        while (num > 0) {
            if (num % 10 == 0) {
                return true;
            }

            num /= 10;
        }

        return false;
    }
}
