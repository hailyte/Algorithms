package sjsu.yu.cs146.project1.part2;

import java.util.Arrays;

public class Quicksort {

	/*
	 * Construct a Quicksort object.
	 */
	public Quicksort() {
		comparisons = 0;
	}

	/*
	 * Return the number of comparisons made so far.
	 */
	public long getPartCount() {
		return comparisons;
	}

	/*
	 * Quicksort with pivot at end.
	 */
	public int[] qs1(int[] array, int start, int end) {
		if (start < end) {
			int p = partition(array, start, end);
			qs1(array, start, p - 1);
			qs1(array, p + 1, end);
		}
		return array;
	}

	/*
	 * Quicksort with pivot at median.
	 */
	public int[] qs2(int[] array, int start, int end) {
		if (start < end) {
			pivot(array, start, end); // find the median of medians to use as the pivot
			swap(array, start, end); // pivot is first element, move it to the end
			int p = partition(array, start, end);
			qs2(array, start, p - 1);
			qs2(array, p + 1, end);
		}
		return array;
	}

	/*
	 * Reset current Quicksort object.
	 */
	public void reset() {
		comparisons = 0;
	}

	/*
	 * Find the kth smallest element in an array.
	 */
	public int select(int[] array, int start, int end, int k) {
		if (start == end) {
			return array[start]; // array of length 1
		}
		int index = partition(array, start, end);
		if (k == index) {
			return array[index]; // partitioned at the kth index
		}
		else if (k < index) {
			return select(array, start, index - 1, k); // index value too high, search lower values
		}
		else {
			return select(array, index + 1, end, k); // index value too low, search higher values
		}
	}

	/*
	 * Rearranges the array so that the median of medians is the first element.
	 */
	private void pivot(int[] array, int start, int end) {
		for (int i = start; i < end; i++) {
			int right = i + 4; // sets the subarray indices from i to i + 4
			if (right > end) {
				right = end; // last subarray may have fewer than 5 elements
			}
			int median = sort5(array, i, right); // sort a group of 5 or less and return the median index
			swap(array, median, i); // put the medians in the first 5 array slots
		}
		select(array, 0, 5, 3); // find the median of the medians
	}

	/*
	 * Sorts a group of 5 or fewer elements and returns the middle index
	 */
	private int sort5(int[] array, int start, int end) {
		Arrays.sort(array, start, end);
		return start + (end - start)/2;
	}

	/*
	 * Partitions an array about a given pivot and returns the index of the split point.
	 */
	private int partition(int[] array, int start, int end) {
		int pivot = array[end]; // pivot at end
		int storeIndex = start;
		for (int i = start; i < end; i++) {
			comparisons++;
			if (array[i] <= pivot) {
				swap(array, i, storeIndex); // push lower elements earlier
				storeIndex++;
			}
		}
		swap(array, storeIndex, end); // move pivot from the end
		return storeIndex;
	}

	/*
	 * Swaps two array elements given their indices
	 */
	private void swap(int[] array, int i1, int i2) {
		if (i1 != i2) {
			int temp = array[i1];
			array[i1] = array[i2];
			array[i2] = temp;
		}
	}

	/*
	 * Generate an array of random integers.
	 */
	public int[] populate(int size) {
		int[] newArray = new int[size];
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = (int) (Math.random()*(size + 1)); // 0 - size to minimize duplicate values
		}
		return newArray;
	}

	private long comparisons;

}
