package commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Utility {
	/**
	 * Check if two collections are equal in terms of content but not equal in terms of type.
	 * @param xs The first collection to compare.
	 * @param ys The second collection to compare.
	 * @return `true' if the contents of both collections are the same, `false' otherwise.
	 */
	public static boolean contentsEqual(Collection<?> xs, Collection<?> ys) {
		HashMap<Object, Integer> xCount = new HashMap<>();
		HashMap<Object, Integer> yCount = new HashMap<>();
		xs.forEach(o -> xCount.put(o, xCount.getOrDefault(o, 0) + 1));
		ys.forEach(o -> yCount.put(o, yCount.getOrDefault(o, 0) + 1));
		return xCount.equals(yCount);
	}

	/**
	 * Find the max value of an arbitrary amount of inputs.  The function signature contains a `T x'
	 * along with a `T... xs' to force the user to provide at least one argument.
	 * @param x The first element to compare.
	 * @param xs The rest of the elements to compare.
	 * @param <T> The type of the objects being compared.
	 * @return The max value of `x' and the elements of `xs'.
	 */
	@SafeVarargs
	public static <T extends Comparable<T>> T max(T x, T... xs) {
		return Arrays
			.stream(xs)
			.reduce(x, (a, b) -> a.compareTo(b) < 0 ? b : a);
	}

	/**
	 * Check if a given collection is either null or empty.
	 * @param xs The collection to check.
	 * @return `true' if the collection is null or empty, `false' otherwise.
	 */
	public static boolean nullOrEmpty(Collection<?> xs) {
		return xs == null || xs.isEmpty();
	}

	/**
	 * Check if a given string is either null or empty.
	 * @param s The string to check.
	 * @return `true' if the string is null or empty, `false' otherwise.
	 */
	public static boolean nullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * Compares two doubles and returns true if they are equal.
	 * @param a The first number.
	 * @param b The second number.
	 * @return True if they are equal and false otherwise.
	 */
	public static boolean doubleEquals(double a, double b) {
		return Math.abs(a - b) < 1e-9;
	}

	/**
	 * Tests whether a given string is numeric.
	 * @param string The string which we test if it's numeric or not.
	 * @return A boolean value telling us if the string is numeric.
	 */
	public static boolean isNumeric(String string) {
		return string != null && string.chars().allMatch(c -> c >= '0' && c <= '9');
	}
}