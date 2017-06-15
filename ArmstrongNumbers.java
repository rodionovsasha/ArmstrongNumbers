import java.util.Set;
import java.util.TreeSet;

public class ArmstrongNumbers {
    private static final int AMOUNT_OF_SIMPLE_DIGITS = 10; // from 0 to 9

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Set<Long> result = getNumbers(Long.MAX_VALUE);

        int i = 1;
        for (long armstrongNumber : result) {
            System.out.println(i + ". " + armstrongNumber);
            i++;
        }
        System.out.println(String.format("Execution time: %dms", (System.currentTimeMillis() - startTime)));
        System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "mb");
    }

    private static Set<Long> getNumbers(final long number) {
        Set<Long> armstrongNumbers = new TreeSet<>();
        int currentAmountOfDigits = (int)Math.log10(number) + 1;  //get amount of digits in a number
        long[][] arrayOfPowers = new long[AMOUNT_OF_SIMPLE_DIGITS][currentAmountOfDigits + 1];
        for (int i = 0; i < AMOUNT_OF_SIMPLE_DIGITS; i++) {
            for (int j = 1; j <= currentAmountOfDigits; j++) {
                arrayOfPowers[i][j] = pow(i, j);
            }
        }

        //Main loop
        for (long i = 1; i < number; i = getNextNumber(i)) {
            if (i < 0) {
                break; // the maximum value is reached
            }

            long sumOfPowers = getSumOfPowers(i, arrayOfPowers);
            if (sumOfPowers <= number && isArmstrongNumber(sumOfPowers, arrayOfPowers)) {
                armstrongNumbers.add(sumOfPowers);
            }
        }

        return armstrongNumbers;
    }

    private static long getNextNumber(long number) {
        if (isGrowingNumber(number)) { // here we have numbers where each digit not less than previous one and not more than next one: 12, 1557, 333 and so on.
            return ++number;
        } else { // here we have numbers which end in zero: 10, 20, ..., 100, 110, 5000, 1000000 and so on.
            long copyOfNumber = number;
            long count = 1;

            while (copyOfNumber % 10 == 0) {// 5000 -> 500 -> 50: try to get the last non-zero digit
                copyOfNumber = copyOfNumber / 10;
                count = count * 10;
            }
            long lastNonZeroDigit = copyOfNumber % 10;

            count = lastNonZeroDigit * count / 10;
            return number + count;
        }
    }

    private static long pow(final int base, final int exponent) {
        long productOfNumbers = 1;
        for (int i = 1; i <= exponent; i++) {
            productOfNumbers = productOfNumbers * base;
        }
        return productOfNumbers;
    }

    /*
    * 135 returns true:  1 < 3 < 5
    * 153 returns false: 1 < 5 > 3
    * */
    private static boolean isGrowingNumber(long number) {
        return (number + 1) % 10 != 1;
    }

    private static long getSumOfPowers(long number, final long[][] arrayOfPowers) {
        int power = (int)Math.log10(number) + 1; // get amount of digits in a number
        long currentSum = 0;
        while (number > 0) {
            currentSum = currentSum + arrayOfPowers[(int)(number % 10)][power]; // get powers from array by indexes and then the sum.
            number = number / 10;
        }
        return currentSum;
    }

    private static boolean isArmstrongNumber(final long number, final long[][] arrayOfPowers) {
        return number == getSumOfPowers(number, arrayOfPowers);
    }
}