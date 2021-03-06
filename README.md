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
4 x Intel(R) Core(TM) i5 CPU 760 @ 2.80GHz  
Memory: 15.7 GiB of RAM  
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
Execution time: 2m54s  (`java.lang.Long` max value)
### n = 20
Execution time: 4m38s  
### n = 21
Execution time: 7m53s  
### n = 23
Execution time: 21m6s  
### n = 24
Execution time: 31m27s  
### n = 25
Execution time: 47m47s  
### n = 26
Execution time: 70m49s  (1.16h)  
### n = 27
Execution time: 103m59s  (1.71h)  
### n = 28
Execution time: 145m3s  (2.36h)  
### n = 29
Execution time: 205m3s  (3.41h)  
### n = 30
Execution time: 285m12s  (4.75h)  
### n = 31
Execution time: 395m20s  (6.58h)  
### n = 32
Execution time: 551m26s  (9.18h)  
### n = 33
Execution time: 764m19s  (12.73h)  
### n = 34
Execution time: 1047m16s  (17.45h)  
### n = 35
Execution time: 1429m46s  (23.81h)  
### n = 36
Execution time: 1914m33s  (31.9h)  
### n = 37
Execution time: 2536m56s  (42.3h)  
### n = 38
Execution time: 2536m56s  (31.9h)  
### n = 39
Execution time: > 3354m10s  (55.9h)  

`ArmstrongNumbersAllV2.java` is also based on `java.math.BigInteger`, but uses another search algorithm. This approach returns all numbers for n <= 40 in `245m17s (4h)`.  

The rest of numbers:  

51. 63105425988599693916
52. 128468643043731391252
53. 449177399146038697307
54. 21887696841122916288858
55. 27879694893054074471405
56. 27907865009977052567814
57. 28361281321319229463398
58. 35452590104031691935943
59. 174088005938065293023722
60. 188451485447897896036875
61. 239313664430041569350093
62. 1550475334214501539088894
63. 1553242162893771850669378
64. 3706907995955475988644380
65. 3706907995955475988644381
66. 4422095118095899619457938
67. 121204998563613372405438066
68. 121270696006801314328439376
69. 128851796696487777842012787
70. 174650464499531377631639254
71. 177265453171792792366489765
72. 14607640612971980372614873089
73. 19008174136254279995012734740
74. 19008174136254279995012734741
75. 23866716435523975980390369295
76. 1145037275765491025924292050346
77. 1927890457142960697580636236639
78. 2309092682616190307509695338915
79. 17333509997782249308725103962772
80. 186709961001538790100634132976990
81. 186709961001538790100634132976991
82. 1122763285329372541592822900204593
83. 12639369517103790328947807201478392
84. 12679937780272278566303885594196922
85. 1219167219625434121569735803609966019
86. 12815792078366059955099770545296129367
87. 115132219018763992565095597973971522400
88. 115132219018763992565095597973971522401
