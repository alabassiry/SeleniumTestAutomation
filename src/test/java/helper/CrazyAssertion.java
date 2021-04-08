package helper;

public class CrazyAssertion {
	private static Throwable cause;

	public static Exception assertEquals(String actual, String expected) throws Exception {
		cause = new Throwable("Assertion Failure!");
		Exception e = new Exception("Expected: " + expected + " but obtained: " + actual, cause);

		if (actual.equals(expected)) {
			// do nothing
		} else {
			throw e;
		}

		return e;
	}

	public static Exception assertEquals(Boolean actual, Boolean expected) throws Exception {
		cause = new Throwable("Assertion Failure!");
		Exception e = new Exception("Expected: " + expected + " but obtained: " + actual, cause);

		if (actual.equals(expected)) {
			// do nothing
		} else {
			throw e;
		}

		return e;
	}

	public static Exception assertEquals(int actual, int expected) throws Exception {
		cause = new Throwable("Assertion Failure!");
		Exception e = new Exception("Expected: " + expected + " but obtained: " + actual, cause);

		if (actual == expected) {
			// do nothing
		} else {
			throw e;
		}

		return e;
	}
}
