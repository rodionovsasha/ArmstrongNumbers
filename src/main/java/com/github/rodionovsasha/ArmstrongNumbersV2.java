package com.github.rodionovsasha;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class ArmstrongNumbersV2 {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final long MAX_NUMBER = Long.MAX_VALUE;
    private static final long[][] ARRAY_OF_POWERS = new long[AMOUNT_OF_SIMPLE_DIGITS][getDigitsAmount(MAX_NUMBER) + 1];
    private static int counter = 1;

    static {
        IntStream.range(1, AMOUNT_OF_SIMPLE_DIGITS).forEach(i ->
                IntStream.rangeClosed(1, getDigitsAmount(MAX_NUMBER)).forEach(j ->
                        ARRAY_OF_POWERS[i][j] = pow(i, j)
                )
        );
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        getNumbers().forEach(n -> System.out.println(counter++ + ". " + n));

        System.out.printf("Execution time: %dms%n", (System.currentTimeMillis() - startTime));
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    private static Set<Long> getNumbers() {
        Set<Long> armstrongNumbers = new TreeSet<>();

        LongStream.iterate(1, i -> i < MAX_NUMBER, ArmstrongNumbersV2::getNextNumber)
                .takeWhile(i -> i > 0)
                .map(ArmstrongNumbersV2::getSumOfPowers)
                .filter(ArmstrongNumbersV2::isArmstrongNumber)
                .forEach(armstrongNumbers::add);

        return armstrongNumbers;
    }

    private static long getNextNumber(final long number) {
        long copyOfNumber = number;
        if (isGrowingNumber(copyOfNumber)) { // here we have numbers where each digit not less than previous one and not more than next one: 12, 1557, 333 and so on.
            return ++copyOfNumber;
        }

        // here we have numbers which end in zero: 10, 20, ..., 100, 110, 5000, 1000000 and so on.
        long lastNumber = 1; //can be: 1,2,3..., 10,20,30,...,100,200,300,...

        while (copyOfNumber % 10 == 0) {// 5000 -> 500 -> 50: try to get the last non-zero digit
            copyOfNumber = copyOfNumber / 10;
            lastNumber = lastNumber * 10;
        }
        long lastNonZeroDigit = copyOfNumber % 10;

        return number + (lastNonZeroDigit * lastNumber / 10); //e.g. number=100, lastNumber=10, lastNonZeroDigit=1
    }

    /**
     * Analog of Math.pow which works with long type
     */
    private static long pow(final int base, final int exponent) {
        return LongStream.rangeClosed(1, exponent).reduce(1, (pow, exp) -> pow * base);
    }

    /*
    * 135 returns true:  1 < 3 < 5
    * 153 returns false: 1 < 5 > 3
    * */
    private static boolean isGrowingNumber(final long number) {
        return (number + 1) % 10 != 1;
    }

    private static long getSumOfPowers(final long number) {
        long currentNumber = number;
        int power = getDigitsAmount(currentNumber);
        long currentSum = 0;
        while (currentNumber > 0) {
            currentSum += ARRAY_OF_POWERS[(int) (currentNumber % 10)][power]; // get powers from array by indexes and then the sum.
            currentNumber /= 10;
        }
        return currentSum;
    }

    private static boolean isArmstrongNumber(final long number) {
        return number == getSumOfPowers(number);
    }

    private static int getDigitsAmount(long number) {
        return (int) Math.log10(number) + 1;
    }
}