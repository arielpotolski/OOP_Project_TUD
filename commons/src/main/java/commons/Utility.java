package commons;

import java.util.Collection;
import java.util.HashMap;

public class Utility {
	/**
	 * Check if two collections are equal in terms of content but not equal in terms of type.
	 * @param xs The first collection to compare.
	 * @param ys The second collection to compare.
	 * @return `true' if the contents of both collections are the same, `false' otherwise.
	 */
	public static boolean contentsEqual(Collection xs, Collection ys) {
		HashMap<Object, Integer> xCount = new HashMap<>();
		HashMap<Object, Integer> yCount = new HashMap<>();
		xs.forEach(o -> xCount.put(o, xCount.getOrDefault(o, 0) + 1));
		ys.forEach(o -> yCount.put(o, yCount.getOrDefault(o, 0) + 1));
		return xCount.equals(yCount);
	}
}