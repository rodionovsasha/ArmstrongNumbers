package com.github.rodionovsasha;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

class ArmstrongNumbersV3 {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9
    private static final BigInteger MAX_NUMBER = valueOf(Long.MAX_VALUE);
    private static final long[][] ARRAY_OF_POWERS = new long[AMOUNT_OF_SIMPLE_DIGITS][getDigitsAmount(MAX_NUMBER) + 1];
    private static int counter = 1;

    static {
        for (int i = 0; i < AMOUNT_OF_SIMPLE_DIGITS; i++) {
            for (int j = 0; j < getDigitsAmount(MAX_NUMBER) + 1; j++) {
                ARRAY_OF_POWERS[i][j] = pow(i, j);
            }
        }
        assert ARRAY_OF_POWERS[0][0] == 1;
        assert ARRAY_OF_POWERS[2][2] == 4;
        assert ARRAY_OF_POWERS[9][4] == 6561;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Set<BigInteger> result = getNumbers();

        for (BigInteger armstrongNumber : result) {
            System.out.println(counter++ + ". " + armstrongNumber);
        }

        long executionTime = System.currentTimeMillis() - startTime;
        long minutes = (executionTime / 1000)  / 60;
        long seconds = (executionTime / 1000) % 60;

        System.out.printf("Execution time: %dm%ds%n", minutes, seconds);
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    private static Set<BigInteger> getNumbers() {
        Set<BigInteger> armstrongNumbers = new TreeSet<>();

        //Main loop
        for (BigInteger i = ONE; i.compareTo(MAX_NUMBER) < 0; i = getNextNumber(i)) { // i < MAX_NUMBER
            if (i.compareTo(BigInteger.ZERO) < 0) {
                break; // the maximum value is reached
            }

            BigInteger sumOfPowers = getSumOfPowers(i);
            if (sumOfPowers.compareTo(MAX_NUMBER) < 1 && isArmstrongNumber(sumOfPowers)) { // sumOfPowers <= MAX_NUMBER
                armstrongNumbers.add(sumOfPowers);
            }
        }

        return armstrongNumbers;
    }

    private static BigInteger getNextNumber(BigInteger number) {
        BigInteger copyOfNumber = number;
        if (isGrowingNumber(copyOfNumber)) { // here we have numbers where each digit not less than previous one and not more than next one: 12, 1557, 333 and so on.
            return copyOfNumber.add(ONE);
        }

        // here we have numbers which end in zero: 10, 20, ..., 100, 110, 5000, 1000000 and so on.
        long lastNumber = 1; //can be: 1,2,3..., 10,20,30,...,100,200,300,...

        while (copyOfNumber.mod(TEN).equals(ZERO)) {// 5000 -> 500 -> 50: try to get the last non-zero digit
            copyOfNumber = copyOfNumber.divide(TEN);
            lastNumber = lastNumber * 10;
        }
        long lastNonZeroDigit = copyOfNumber.mod(TEN).longValue();

        return number.add(valueOf(lastNonZeroDigit * lastNumber / 10)); //e.g. number=100, lastNumber=10, lastNonZeroDigit=1
    }

    /**
     * Analog of Math.pow which works with long type
     */
    private static long pow(int base, int exponent) {
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
    private static boolean isGrowingNumber(BigInteger number) {
        //return (number + 1) % 10 != 1;
        return !number.add(ONE).mod(TEN).equals(ONE);
    }

    private static BigInteger getSumOfPowers(BigInteger number) {
        BigInteger currentNumber = number;
        int power = getDigitsAmount(currentNumber);
        BigInteger currentSum = ZERO;
        while (currentNumber.compareTo(ZERO) > 0) { // currentNumber > 0
            currentSum = currentSum.add(valueOf(ARRAY_OF_POWERS[currentNumber.mod(TEN).intValue()][power])); // get powers from array by indexes and then the sum.
            currentNumber = currentNumber.divide(TEN);
        }
        return currentSum;
    }

    private static boolean isArmstrongNumber(BigInteger number) {
        return number.equals(getSumOfPowers(number));
    }

    private static int getDigitsAmount(BigInteger number) {
        double factor = Math.log(2) / Math.log(10);
        int digitCount = (int) (factor * number.bitLength() + 1);
        if (TEN.pow(digitCount - 1).compareTo(number) > 0) {
            return digitCount - 1;
        }
        return digitCount;
    }
}