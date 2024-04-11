/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out get checks. */
	public static boolean checkGet(int expected, int actual) {
		if (expected != actual) {
			System.out.println("get() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");

		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);
	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running addFirst/removeFirst test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);
	}

	/** Adds an item to last, then removes an item to last, and ensures that dll is empty afterwards. */
	public static void addRemoveLastTest() {
		System.out.println("Running addLast/removeLast test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addLast(10);
		// should not be empty
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeLast();
		// should be empty
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);
	}

	public static void addGetTest() {
		System.out.println("Running get/getRecursive test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be null
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be 10
		passed = checkGet(10, lld1.get(0)) && passed;

		lld1.addFirst(5);
		// should be 5
		passed = checkGet(5, lld1.get(0)) && passed;

		lld1.addLast(15);
		// should be 15
		passed = checkGet(15, lld1.getRecursive(2)) && passed;

		lld1.addLast(20);
		// should be 20
		passed = checkGet(20, lld1.getRecursive(3)) && passed;

		printTestStatus(passed);
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();
		addRemoveLastTest();
		addGetTest();
	}
} 