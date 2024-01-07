package com.github.rodionovsasha;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;

class ArmstrongNumbersAll {
    private static final int MAX_DIGITS_AMOUNT = 39; // max digits amount where armstrong numbers exist(39)
    private static final double FACTOR = Math.log(2) / Math.log(10);
    private static int counter = 1;

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
        for (BigInteger i = ONE; getDigitsAmount(i) <= MAX_DIGITS_AMOUNT; i = getNextNumber(i)) {
            BigInteger sumOfPowers = getSumOfPowers(i);
            if (isArmstrongNumber(sumOfPowers)) {
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
        BigInteger lastNumber = ONE; //can be: 1,2,3..., 10,20,30,...,100,200,300,...

        while (copyOfNumber.mod(TEN).equals(ZERO)) {// 5000 -> 500 -> 50: try to get the last non-zero digit
            copyOfNumber = copyOfNumber.divide(TEN);
            lastNumber = lastNumber.multiply(TEN);
        }
        BigInteger lastNonZeroDigit = copyOfNumber.mod(TEN);

        return number.add(lastNonZeroDigit.multiply(lastNumber).divide(TEN)); //e.g. number=100, lastNumber=10, lastNonZeroDigit=1
    }

    /*
    * 135 returns true:  1 < 3 < 5
    * 153 returns false: 1 < 5 > 3
    * */
    private static boolean isGrowingNumber(BigInteger number) {
        return !number.add(ONE).mod(TEN).equals(ONE);
    }

    private static BigInteger getSumOfPowers(BigInteger number) {
        BigInteger currentNumber = number;
        int power = getDigitsAmount(currentNumber);
        BigInteger currentSum = ZERO;
        while (currentNumber.compareTo(ZERO) > 0) { // currentNumber > 0
            currentSum = currentSum.add(currentNumber.mod(TEN).pow(power)); // get powers from array by indexes and then the sum.
            currentNumber = currentNumber.divide(TEN);
        }
        return currentSum;
    }

    private static boolean isArmstrongNumber(BigInteger number) {
        return number.equals(getSumOfPowers(number));
    }

    private static int getDigitsAmount(BigInteger number) {
        int digitCount = (int) (FACTOR * number.bitLength() + 1);
        if (TEN.pow(digitCount - 1).compareTo(number) > 0) {
            return digitCount - 1;
        }
        return digitCount;
    }
}