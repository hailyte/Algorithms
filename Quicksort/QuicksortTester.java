package sjsu.yu.cs146.project1.part2;

//import java.util.Arrays;

public class QuicksortTester {

	public static void main(String[] args) {

		final int SIZE = 10000;
		Quicksort sorter = new Quicksort();
		int[] arr = sorter.populate(SIZE);

		long start = System.currentTimeMillis();
		sorter.qs1(arr, 0, arr.length - 1);
	    long end = System.currentTimeMillis();
	    long elapsed = end - start;
	    System.out.println("QS1 time to sort 10000 elements in s: "+ elapsed/1000.0);
		System.out.println(sorter.getPartCount());

/*	    System.out.println(Arrays.toString(arr));
	    System.out.println(sorter.select(arr, 0, arr.length - 1, arr.length/2));
		int[] arr2 = sorter.qs1(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr2));
		int[] arr3 = sorter.qs2(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr3));
*/	}

}
