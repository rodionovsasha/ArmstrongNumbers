package com.github.rodionovsasha;

import java.util.Arrays;

/**
 * Search and prints all Armstrong Numbers for the limit {@link Long#MAX_VALUE}.
 * <a href="https://mathworld.wolfram.com/NarcissisticNumber.html">Armstrong Numbers</a>
 */
public class ArmstrongNumbers {
    private static final int DIGITS = 10;
    private static final long MAX_NUMBER = Long.MAX_VALUE;
    private static final int INITIAL_RESULT_SIZE = 64;
    private static final long[] FIRST_50_NUMBERS = {
            1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L,
            153L, 370L, 371L, 407L,
            1634L, 8208L, 9474L,
            54748L, 92727L, 93084L,
            548834L,
            1741725L, 4210818L, 9800817L, 9926315L,
            24678050L, 24678051L, 88593477L,
            146511208L, 472335975L, 534494836L, 912985153L,
            4679307774L,
            32164049650L, 32164049651L, 40028394225L, 42678290603L,
            44708635679L, 49388550606L, 82693916578L, 94204591914L,
            28116440335967L,
            4338281769391370L, 4338281769391371L,
            21897142587612075L, 35641594208964132L, 35875699062250035L,
            1517841543307505039L, 3289582984443187032L,
            4498128791164624869L, 4929273885928088826L
    };

    private static final int[] FIRST_50_DIGIT_COUNTS = {
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            3, 3, 3, 3,
            4, 4, 4,
            5, 5, 5,
            6,
            7, 7, 7, 7,
            8, 8, 8,
            9, 9, 9, 9,
            10,
            11, 11, 11, 11, 11, 11, 11, 11,
            14,
            16, 16,
            17, 17, 17,
            19, 19, 19, 19
    };

    private final long limit;
    private final int[] digitCounts = new int[DIGITS];
    private final int[] checkedDigitCounts = new int[DIGITS];
    private final long[] powers = new long[DIGITS];
    private final int[] maxDigitCounts = new int[DIGITS];
    private long[] result = new long[INITIAL_RESULT_SIZE];
    private int resultSize;
    private int digitCount;

    private static int counter = 1;

    /**
     * Prints all Armstrong numbers that fit into the signed {@code long} range.
     */
    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        long[] armstrongNumbers = getNumbers(MAX_NUMBER);

        assertKnownNumbers(armstrongNumbers);

        for (long armstrongNumber : armstrongNumbers) {
            System.out.println(counter++ + ". " + armstrongNumber);
        }

        System.out.printf("Execution time: %dms%n", (System.currentTimeMillis() - startTime));
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    /**
     * Finds all Armstrong numbers among natural numbers strictly less than {@code n}.
     *
     * @param n exclusive upper bound
     * @return Armstrong numbers in ascending order
     */
    public static long[] getNumbers(long n) {
        if (n <= 1) {
            return new long[0];
        }

        return new ArmstrongNumbers(n).find();
    }

    /**
     * Creates a search context for one exclusive upper bound.
     */
    private ArmstrongNumbers(long limit) {
        this.limit = limit;
    }

    /**
     * Iterates over possible digit lengths and collects matching sums.
     */
    private long[] find() {
        int maxDigits = getDigitsAmount(limit - 1);

        initPowers();
        for (digitCount = 1; digitCount <= maxDigits; digitCount++) {
            updatePowersAndMaxDigitCounts();
            findDigitCombinations(9, digitCount, 0);
        }

        return sortedResult();
    }

    /**
     * Prepares cached powers before the first digit-length iteration.
     */
    private void initPowers() {
        powers[0] = 0;
        maxDigitCounts[0] = Integer.MAX_VALUE;

        for (int digit = 1; digit < DIGITS; digit++) {
            powers[digit] = 1;
        }
    }

    /**
     * Advances cached powers to {@code digit^digitCount} and caches maximum
     * useful counts for the current digit length.
     */
    private void updatePowersAndMaxDigitCounts() {
        for (int digit = 1; digit < DIGITS; digit++) {
            powers[digit] *= digit;

            long maxCount = (limit - 1) / powers[digit];
            maxDigitCounts[digit] = maxCount < Integer.MAX_VALUE ? (int) maxCount : Integer.MAX_VALUE;
        }
    }

    /**
     * Recursively enumerates all digit-count combinations for the current length.
     *
     * <p>For example, for a three-digit number it checks combinations like
     * two {@code 3}s and one {@code 7}, without trying every permutation
     * {@code 337}, {@code 373}, {@code 733} separately.</p>
     */
    private void findDigitCombinations(int digit, int rest, long sum) {
        if (digit == 0) {
            digitCounts[0] = rest;
            checkCurrentCombination(sum);
            return;
        }

        int maxCount = Math.min(rest, maxDigitCounts[digit]);
        long currentSum = sum;

        for (int count = 0; count <= maxCount; count++) {
            digitCounts[digit] = count;
            findDigitCombinations(digit - 1, rest - count, currentSum);

            if (count < maxCount) {
                if (powers[digit] > limit - 1 - currentSum) {
                    break;
                }
                currentSum += powers[digit];
            }
        }
    }

    /**
     * Keeps the already calculated Armstrong sum if it has exactly the same
     * digits as the current digit-count combination.
     */
    private void checkCurrentCombination(long sum) {
        if (sum > 0 && hasSameDigits(sum)) {
            addResult(sum);
        }
    }

    /**
     * Checks that {@code number}'s digit multiset equals the currently selected
     * digit-count combination.
     */
    private boolean hasSameDigits(long number) {
        System.arraycopy(digitCounts, 0, checkedDigitCounts, 0, DIGITS);

        int currentDigitCount = 0;
        while (number > 0) {
            int digit = (int) (number % 10);
            checkedDigitCounts[digit]--;
            if (checkedDigitCounts[digit] < 0) {
                return false;
            }

            number /= 10;
            currentDigitCount++;
        }

        return currentDigitCount == digitCount;
    }

    /**
     * Adds a number to the compact result buffer.
     */
    private void addResult(long number) {
        if (resultSize == result.length) {
            result = Arrays.copyOf(result, result.length * 2);
        }

        result[resultSize++] = number;
    }

    /**
     * Returns found numbers in ascending order.
     */
    private long[] sortedResult() {
        long[] sorted = Arrays.copyOf(result, resultSize);
        Arrays.sort(sorted);
        return sorted;
    }

    /**
     * Counts decimal digits without floating-point rounding.
     */
    private static int getDigitsAmount(long number) {
        int digits = 0;
        do {
            digits++;
            number /= 10;
        } while (number > 0);

        return digits;
    }

    /**
     * Verifies the first 50 known base-10 Armstrong numbers and their digit
     * counts from the MathWorld Narcissistic Number table.
     */
    private static void assertKnownNumbers(long[] actual) {
        assert Arrays.equals(FIRST_50_NUMBERS, actual) :
                "Unexpected Armstrong numbers: " + Arrays.toString(actual);

        for (int i = 0; i < FIRST_50_NUMBERS.length; i++) {
            assert getDigitsAmount(FIRST_50_NUMBERS[i]) == FIRST_50_DIGIT_COUNTS[i] :
                    "Unexpected digit count for " + FIRST_50_NUMBERS[i];
        }
    }
}
