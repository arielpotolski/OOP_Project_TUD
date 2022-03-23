package commons;

import java.util.Collection;

public class Utilities {
	/**
	 * Check if two collections are equal in terms of content but not equal in terms of type.
	 * @param xs The first collection to compare.
	 * @param ys The second collection to compare.
	 * @return `true' if the contents of both collections are the same, `false' otherwise.
	 */
	public static boolean contentsEqual(Collection xs, Collection ys) {
		return xs.size() == ys.size() && xs.containsAll(ys);
	}
}