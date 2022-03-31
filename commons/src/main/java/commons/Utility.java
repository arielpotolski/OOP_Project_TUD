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
                                                  
                                                /**
                       * Check if a given collection is either null or empty.
                                * @param xs The collection to check.
              * @return `true' if the collection is null or empty, `false' otherwise.
                                                 */
                         public static boolean nullOrEmpty(Collection xs) {
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
                     * Compares two doubles and returns true if they are equal
                                    * @param a the first number
                                    * @param b the second number
                        * @return true if they are equal and false otherwise
                                                 */
                      public static boolean doubleEquals(double a, double b) {
                                   return Math.abs(a - b) < 1e-9;
                                                 }
                                                  }