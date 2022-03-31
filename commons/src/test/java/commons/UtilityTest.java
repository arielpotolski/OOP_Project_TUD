                                          package commons;
                                                  
                                    import java.util.ArrayList;
                                    import java.util.Collection;
                                     import java.util.HashSet;
                                    import java.util.LinkedList;
                                       import java.util.List;
                                                  
                            import static commons.Utility.contentsEqual;
                             import static commons.Utility.nullOrEmpty;
                                                  
                              import org.junit.jupiter.api.BeforeEach;
                                 import org.junit.jupiter.api.Test;
                    import static org.junit.jupiter.api.Assertions.assertFalse;
                     import static org.junit.jupiter.api.Assertions.assertTrue;
                                                  
                                        class UtilityTest {
                                       ArrayList<String> xs;
                                      LinkedList<Integer> ys;
                                        HashSet<Integer> zs;
                                                  
                                            @BeforeEach
                                       public void setup() {
                       this.xs = new ArrayList<>(List.of("Hello", "World"));
                           this.ys = new LinkedList<>(List.of(1, 2, 3));
                                     this.zs = new HashSet<>();
                                                  
                                          this.zs.add(1);
                                          this.zs.add(2);
                                          this.zs.add(3);
                                                 }
                                                  
                                               @Test
                            public void contentsNotEqualSameTypeTest() {
                               var ys = (Collection) this.xs.clone();
                                           ys.add("Foo");
                              assertFalse(contentsEqual(this.xs, ys));
                                         ys.remove("Foo");
                              assertTrue(contentsEqual(this.xs, ys));
                                       this.xs.add("Hello");
                                          ys.add("World");
                              assertFalse(contentsEqual(this.xs, ys));
                                                 }
                                                  
                                               @Test
                         public void contentsNotEqualDifferentTypeTest() {
                           assertFalse(contentsEqual(this.xs, this.ys));
                                                 }
                                                  
                                               @Test
                           public void contentsEqualDifferentTypeTest() {
                            assertTrue(contentsEqual(this.ys, this.zs));
                                                 }
                                                  
                                               @Test
                             public void contentsEqualSameTypeTest() {
                 assertTrue(contentsEqual(this.ys, (Collection) this.ys.clone()));
                                                 }
                                                  
                                               @Test
                               public void nullOrEmptyStringTest() {
                                assertFalse(nullOrEmpty("Hunter2"));
                                                 }
                                                  
                                               @Test
                             public void nullOrEmptyEmptyStringTest() {
                                    assertTrue(nullOrEmpty(""));
                                                 }
                                                  
                                               @Test
                                public void nullOrEmptyNullTest() {
                              assertTrue(nullOrEmpty((String) null));
                            assertTrue(nullOrEmpty((Collection) null));
                                                 }
                                                  
                                               @Test
                           public void nullOrEmptyEmptyCollectionTest() {
                             assertTrue(nullOrEmpty(new ArrayList()));
                                                 }
                                                  
                                               @Test
                             public void nullOrEmptyCollectionTest() {
                                 assertFalse(nullOrEmpty(this.xs));
                                                 }
                                                  
                                               @Test
                                    public void doubleEquals() {
                                       double a = 1.0 / 3.0;
                                       double b = 1.0 / 3.0;
                                       double c = 1.0 / 4.0;
                              assertFalse(Utility.doubleEquals(a, c));
                              assertTrue(Utility.doubleEquals(a, b));
                                                 }
                                                  }