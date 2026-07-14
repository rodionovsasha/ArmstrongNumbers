package com.github.rodionovsasha;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;

/**
 * Searches for all known base-10 Armstrong numbers using {@link BigInteger}
 * arithmetic.
 * MacBook Pro
 * Apple M1 Pro
 * Memory 16 GB
 * Execution time: 24m48s
 * Used memory: 465mb
 */
public class ArmstrongNumbersBeyondLong {
    private static final int DIGITS = 10;
    private static final int MAX_DIGITS = 39;
    private static final BigInteger FIRST_NATURAL_NUMBER = ONE;
    private static final BigInteger BEYOND_LONG_LOWER_BOUND = BigInteger.valueOf(Long.MAX_VALUE).add(ONE);
    private static final BigInteger DEFAULT_UPPER_BOUND = TEN.pow(MAX_DIGITS);

    private final BigInteger lowerBound;
    private final BigInteger upperBound;
    private final boolean printNumbers;
    private final int[] digitCounts = new int[DIGITS];
    private final int[] checkedDigitCounts = new int[DIGITS];
    private final BigInteger[] powers = new BigInteger[DIGITS];
    private final int[] maxDigitCounts = new int[DIGITS];
    private final List<BigInteger> result = new ArrayList<>();
    private int digitCount;

    private static int counter = 1;

    /**
     * Prints all known base-10 Armstrong numbers.
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        new ArmstrongNumbersBeyondLong(FIRST_NATURAL_NUMBER, DEFAULT_UPPER_BOUND, true).find();

        long executionTime = System.currentTimeMillis() - startTime;
        long minutes = (executionTime / 1000) / 60;
        long seconds = (executionTime / 1000) % 60;

        System.out.printf("Execution time: %dm%ds%n", minutes, seconds);
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    /**
     * Finds all known base-10 Armstrong numbers.
     *
     * @return Armstrong numbers in ascending order
     */
    public static BigInteger[] getNumbers() {
        return getNumbers(DEFAULT_UPPER_BOUND);
    }

    /**
     * Finds Armstrong numbers strictly less than {@code upperBound}.
     *
     * @param upperBound exclusive upper bound
     * @return Armstrong numbers in ascending order
     */
    public static BigInteger[] getNumbers(BigInteger upperBound) {
        return getNumbers(FIRST_NATURAL_NUMBER, upperBound);
    }

    /**
     * Finds Armstrong numbers greater than {@link Long#MAX_VALUE}.
     *
     * @return Armstrong numbers in ascending order
     */
    public static BigInteger[] getNumbersBeyondLong() {
        return getNumbers(BEYOND_LONG_LOWER_BOUND, DEFAULT_UPPER_BOUND);
    }

    /**
     * Finds Armstrong numbers in the half-open interval
     * [{@code lowerBound}, {@code upperBound}).
     *
     * @param lowerBound inclusive lower bound
     * @param upperBound exclusive upper bound
     * @return Armstrong numbers in ascending order
     */
    public static BigInteger[] getNumbers(BigInteger lowerBound, BigInteger upperBound) {
        BigInteger normalizedLowerBound = lowerBound.max(ONE);
        if (upperBound.compareTo(normalizedLowerBound) <= 0) {
            return new BigInteger[0];
        }

        return new ArmstrongNumbersBeyondLong(normalizedLowerBound, upperBound).find();
    }

    /**
     * Creates a search context for one BigInteger range.
     */
    private ArmstrongNumbersBeyondLong(BigInteger lowerBound, BigInteger upperBound) {
        this(lowerBound, upperBound, false);
    }

    /**
     * Creates a search context for one BigInteger range with optional console
     * output.
     */
    private ArmstrongNumbersBeyondLong(BigInteger lowerBound, BigInteger upperBound, boolean printNumbers) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.printNumbers = printNumbers;
    }

    /**
     * Iterates over possible digit lengths and collects matching sums.
     */
    private BigInteger[] find() {
        int minDigits = getDigitsAmount(lowerBound);
        int maxDigits = Math.min(MAX_DIGITS, getDigitsAmount(upperBound.subtract(ONE)));

        initPowers();
        for (digitCount = 1; digitCount <= maxDigits; digitCount++) {
            updatePowersAndMaxDigitCounts();
            if (digitCount >= minDigits) {
                int resultSizeBeforeCurrentDigitCount = result.size();
                findDigitCombinations(9, digitCount, ZERO);
                printFoundNumbers(resultSizeBeforeCurrentDigitCount);
            }
        }

        result.sort(Comparator.naturalOrder());
        return result.toArray(new BigInteger[0]);
    }

    /**
     * Prints numbers found for the current digit length while preserving
     * ascending order inside that length.
     */
    private void printFoundNumbers(int fromIndex) {
        if (!printNumbers || fromIndex == result.size()) {
            return;
        }

        result.subList(fromIndex, result.size()).sort(Comparator.naturalOrder());
        for (int i = fromIndex; i < result.size(); i++) {
            System.out.println(counter++ + ". " + result.get(i));
        }
    }

    /**
     * Prepares cached powers before the first digit-length iteration.
     */
    private void initPowers() {
        powers[0] = ZERO;
        maxDigitCounts[0] = Integer.MAX_VALUE;

        for (int digit = 1; digit < DIGITS; digit++) {
            powers[digit] = ONE;
        }
    }

    /**
     * Advances cached powers to {@code digit^digitCount} and caches maximum
     * useful counts for the current digit length.
     */
    private void updatePowersAndMaxDigitCounts() {
        BigInteger maxAllowed = upperBound.subtract(ONE);

        for (int digit = 1; digit < DIGITS; digit++) {
            powers[digit] = powers[digit].multiply(BigInteger.valueOf(digit));

            BigInteger maxCount = maxAllowed.divide(powers[digit]);
            maxDigitCounts[digit] = maxCount.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0
                    ? maxCount.intValue()
                    : Integer.MAX_VALUE;
        }
    }

    /**
     * Recursively enumerates digit-count combinations for the current length.
     */
    private void findDigitCombinations(int digit, int rest, BigInteger sum) {
        if (digit == 0) {
            digitCounts[0] = rest;
            checkCurrentCombination(sum);
            return;
        }

        int maxCount = Math.min(rest, maxDigitCounts[digit]);
        BigInteger currentSum = sum;

        for (int count = 0; count <= maxCount; count++) {
            digitCounts[digit] = count;
            findDigitCombinations(digit - 1, rest - count, currentSum);

            if (count < maxCount) {
                currentSum = currentSum.add(powers[digit]);
                if (currentSum.compareTo(upperBound) >= 0) {
                    break;
                }
            }
        }
    }

    /**
     * Keeps the already calculated Armstrong sum if it is inside the requested
     * range and has exactly the same digits as the current combination.
     */
    private void checkCurrentCombination(BigInteger sum) {
        if (sum.compareTo(lowerBound) >= 0 && sum.compareTo(upperBound) < 0 && hasSameDigits(sum)) {
            result.add(sum);
        }
    }

    /**
     * Checks that {@code number}'s digit multiset equals the current digit-count
     * combination.
     */
    private boolean hasSameDigits(BigInteger number) {
        System.arraycopy(digitCounts, 0, checkedDigitCounts, 0, DIGITS);

        int currentDigitCount = 0;
        BigInteger currentNumber = number;
        while (currentNumber.compareTo(ZERO) > 0) {
            BigInteger[] quotientAndRemainder = currentNumber.divideAndRemainder(TEN);
            int digit = quotientAndRemainder[1].intValue();

            checkedDigitCounts[digit]--;
            if (checkedDigitCounts[digit] < 0) {
                return false;
            }

            currentNumber = quotientAndRemainder[0];
            currentDigitCount++;
        }

        return currentDigitCount == digitCount;
    }

    /**
     * Counts decimal digits for a positive {@link BigInteger}.
     */
    private static int getDigitsAmount(BigInteger number) {
        return number.toString().length();
    }
}
