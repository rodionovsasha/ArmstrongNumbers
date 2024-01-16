package com.github.rodionovsasha;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Search and prints all Happy Numbers for the limit 1_000_000.
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
 * <p>
 * Execution time: 0s
 * Used memory: 39mb
 */
class HappyNumbers {
    private static final long MAX_NUMBER = 1_000_000;

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        for (long i = 1; i < MAX_NUMBER; i = getNextNumber(i)) {
            if (CheckHappyNumber.isHappyNumber(i)) {
                System.out.printf("%d%n", i);
                findPermutation(i);
            }
        }

        System.out.printf("Execution time: %ds%n", (System.currentTimeMillis() - startTime) / 1000);
        System.out.printf("Used memory: %dmb", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
    }

    private static long getNextNumber(long number) {
        var copyOfNumber = number;
        if (isGrowingNumber(copyOfNumber)) { // here we have numbers where each digit not less than previous one and not more than next one: 12, 1557, 333 and so on.
            return ++copyOfNumber;
        }

        // here we have numbers which end in zero: 10, 20, ..., 100, 110, 5000, 1000000 and so on.
        var lastNumber = 1L; //can be: 1,2,3..., 10,20,30,...,100,200,300,...

        while (copyOfNumber % 10 == 0) {// 5000 -> 500 -> 50: try to get the last non-zero digit
            copyOfNumber = copyOfNumber / 10;
            lastNumber = lastNumber * 10;
        }
        var lastNonZeroDigit = copyOfNumber % 10;

        return number + (lastNonZeroDigit * lastNumber / 10); //e.g. number=100, lastNumber=10, lastNonZeroDigit=1
    }

    /*
     * 135 returns true:  1 < 3 < 5
     * 153 returns false: 1 < 5 > 3
     * */
    private static boolean isGrowingNumber(long number) {
        return (number + 1) % 10 != 1;
    }

    private static void findPermutation(long number) {
        long temp = number, count = 0;
        //iteration over the specified digit
        while (temp > 0) {
            //increments the count variable by 1 i the above condition returns true
            count++;
            //divides the variable temp by 10
            temp = temp / 10;
        }

        //using vector to print the permutation of N
        long[] num = new long[(int) count];
        // Store digits of N
        // in the vector num
        while (number > 0) {
            //finds the remainder and store the digit in vector num
            num[(int) (count-- - 1)] = number % 10;
            number = number / 10;
        }

        //iterate over each permutation and find the permutations that are greater than N
        while (findsNextPermutation(num)) {
            //print all the permutations of N
            System.out.printf("%s%n", Arrays.stream(num).mapToObj(String::valueOf).collect(Collectors.joining()));
        }
    }

    //Find all the permutation numbers greater than the number itself
    private static boolean findsNextPermutation(long[] p) {
        for (int a = p.length - 2; a >= 0; --a)
            if (p[a] < p[a + 1]) for (int b = p.length - 1; ; --b)
                if (p[b] > p[a]) {
                    long t = p[a];
                    p[a] = p[b];
                    p[b] = t;
                    for (++a, b = p.length - 1; a < b; ++a, --b) {
                        t = p[a];
                        p[a] = p[b];
                        p[b] = t;
                    }
                    return true;
                }

        return false;
    }
}
