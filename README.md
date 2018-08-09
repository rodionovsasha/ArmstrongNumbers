# Armstrong Numbers
Implementation of the Armstrong numbers (https://en.wikipedia.org/wiki/Narcissistic_number) search algorithm on Java.

The number S consists of M digits, for example, S = 370 and M (number of digits) = 3.
It is necessary to implement the logic of the method, which must be among natural numbers less than N (long) to find all numbers, satisfying the following criterion:
the number S is equal to the sum of its digits raised to the power of M. The program must return all such numbers in ascending order.

Example of the number:
370 = 3 * 3 * 3 + 7 * 7 * 7 + 0 * 0 * 0
8208 = 8 * 8 * 8 * 8 + 2 * 2 * 2 * 2 + 0 * 0 * 0 * 0 + 8 * 8 * 8 * 8
The execution time must not be more then 10 seconds and used memory limit is 50 mb.

# Hardware
8 x Intel(R) Core(TM) i7-4790 CPU @ 3.60GHz
# Output v1 (java 7)
Execution time: 2234ms
Used memory: 5mb
# Output v2 (java 8 + stream API)
Execution time: 2135ms
Used memory: 4mb
# Output v3 (java.math.BigInteger)
Execution time: 2m33s
Used memory: 94mb

1. 1
2. 2
3. 3
4. 4
5. 5
6. 6
7. 7
8. 8
9. 9
10. 153
11. 370
12. 371
13. 407
14. 1634
15. 8208
16. 9474
17. 54748
18. 92727
19. 93084
20. 548834
21. 1741725
22. 4210818
23. 9800817
24. 9926315
25. 24678050
26. 24678051
27. 88593477
28. 146511208
29. 472335975
30. 534494836
31. 912985153
32. 4679307774
33. 32164049650
34. 32164049651
35. 40028394225
36. 42678290603
37. 44708635679
38. 49388550606
39. 82693916578
40. 94204591914
41. 28116440335967
42. 4338281769391370
43. 4338281769391371
44. 21897142587612075
45. 35641594208964132
46. 35875699062250035
47. 1517841543307505039
48. 3289582984443187032
49. 4498128791164624869
50. 4929273885928088826

These numbers exist for only 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 16, 17, 19, 20, 21, 23, 24, 25, 27, 29, 31, 32, 33, 34, 35, 37, 38, and 39 digits.
The full list of the numbers is here: http://mathworld.wolfram.com/NarcissisticNumber.html

`ArmstrongNumbersAll.java` is used to find all armstrong numbers (based on `java.math.BigInteger`).
# Output all numbers
### n = 19
Execution time: 2m33s
Used memory: 94mb
