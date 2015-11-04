For the sake of simplicity, I only counted comparisons when partitioning. Of course, this means extra comparisons will be counted if I choose to use quicksort with the median as the pivot.

Array Size		| 10,000 | 100,000 | 1,000,000 | 10,000,000 | 100,000,000
------------------------|--------|---------|-----------|------------|------------
Total time (s) of QS1	| 0.004	|0.018 | 0.12 | 1.24 | 14.198
Total time (s) of QS2	|0.017 |0.078|0.572|6.733|80.646
Comparisons in QS1	|150,473|2,022,707|25,070,218|298,848,991|3,340,921,728
Comparisons in QS2	|240,314|2,917,897|34,611,541|402,425,484|4,535,485,128

Using quicksort with the median as the pivot lowers the worst case running time of quicksort to O(n log n), as opposed to O(n^2) when used with the first or last element as the pivot.
But calculating the median itself is already pretty expensive. O(n^2) quicksort happens so rarely that using the O(n) median of medians algorithm to guarantee O(n log n) quicksort isn't worth it.