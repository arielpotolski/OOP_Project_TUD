                                          package commons;
                                                  
                                import java.awt.image.BufferedImage;
                               import java.io.ByteArrayOutputStream;
                                    import java.io.IOException;
                                       import java.util.List;
                                     import java.util.Objects;
                                                  
                                   import javax.imageio.ImageIO;
                              import org.junit.jupiter.api.BeforeEach;
                                 import org.junit.jupiter.api.Test;
                 import static org.junit.jupiter.api.Assertions.assertArrayEquals;
                    import static org.junit.jupiter.api.Assertions.assertEquals;
                  import static org.junit.jupiter.api.Assertions.assertNotEquals;
                   import static org.junit.jupiter.api.Assertions.assertNotNull;
                                                  
                                        class ActivityTest {
                                                  
                                    private Activity activity1;
                                    private Activity activity2;
                                    private Activity activity3;
                                                  
                                            @BeforeEach
                                 void setUp() throws IOException {
               activity1 = new Activity("123", "title", 230, "pathpng", "some site");
               activity2 = new Activity("123", "title", 230, "pathpng", "some site");
              activity3 = new Activity("1233", "title", 230, "pathjpg", "some site");
                                                 }
                                                  
                                               @Test
                                  public void checkConstructor() {
                                     assertNotNull(activity1);
                                                 }
                                                  
                                                  
                                               @Test
                                           void getId() {
                              assertEquals("123", activity1.getId());
                                                 }
                                                  
                                               @Test
                                         void getTitle() {
                            assertEquals("title", activity1.getTitle());
                                                 }
                                                  
                                               @Test
                                    void getConsumptionInWh() {
                         assertEquals(230, activity1.getConsumptionInWh());
                                                 }
                                                  
                                               @Test
                                       void getImagePath() {
                         assertEquals("pathpng", activity1.getImagePath());
                                                 }
                                                  
                                               @Test
                                         void getSource() {
                         assertEquals("some site", activity1.getSource());
                                                 }
                                                  
                                                  
                                               @Test
                                           void setId() {
                                      activity1.setId("234");
                              assertEquals("234", activity1.getId());
                                                 }
                                                  
                                               @Test
                                         void setTitle() {
                                   activity1.setTitle("title2");
                           assertEquals("title2", activity1.getTitle());
                                                 }
                                                  
                                               @Test
                                    void setConsumptionInWh() {
                                 activity1.setConsumptionInWh(420);
                         assertEquals(420, activity1.getConsumptionInWh());
                                                 }
                                                  
                                                  
                                               @Test
                                       void setImagePath() {
                                activity1.setImagePath("imagegif");
                        assertEquals("imagegif", activity1.getImagePath());
                                                 }
                                                  
                                               @Test
                                         void setSource() {
                              activity1.setSource("some other site");
                      assertEquals("some other site", activity1.getSource());
                                                 }
                                                  
                                               @Test
                                     void testEqualsMethod() {
                                assertEquals(activity1, activity2);
                               assertNotEquals(activity1, activity3);
                                                 }
                                                  
                                               @Test
                                      void testHashMethod() {
                     assertEquals(activity1.hashCode(), activity2.hashCode());
                                                 }
                                                  
                                               @Test
                                         void makeFake() {
                                assertEquals(activity1, activity2);
                               activity1.makeFake(List.of(40L, 30L));
                               assertNotEquals(activity1, activity2);
                       assertNotEquals(30L, activity1.getConsumptionInWh());
                       assertNotEquals(40L, activity1.getConsumptionInWh());
                                                 }
                                                  
                                               @Test
                                       void toStringTest() {
                                         String expected =
      "ID: 123\nTitle: title\nConsumption in Wh: 230\nImage Path: pathpng\nSource: some site";
                           assertEquals(expected, activity1.toString());
                                                 }
                                                  
                                               @Test
                            void getImageInArray() throws IOException {
                               BufferedImage bufferedImage = ImageIO
                                           .read(Objects
                                      .requireNonNull(Activity
                                               .class
                                         .getClassLoader()
                             .getResourceAsStream("IMGNotFound.jpg")));
                      ByteArrayOutputStream bos = new ByteArrayOutputStream();
                             ImageIO.write(bufferedImage, "jpg", bos);
                 assertArrayEquals(bos.toByteArray(), activity1.getImageInArray());
                                                 }
                                                  
                                               @Test
                            void setImageInArray() throws IOException {
                               BufferedImage bufferedImage = ImageIO
                                           .read(Objects
                                      .requireNonNull(Activity
                                               .class
                                         .getClassLoader()
                             .getResourceAsStream("IMGNotFound.jpg")));
                      ByteArrayOutputStream bos = new ByteArrayOutputStream();
                             ImageIO.write(bufferedImage, "jpg", bos);
                           activity1.setImageInArray(new byte[] {0, 1});
                 assertArrayEquals(new byte[] {0, 1}, activity1.getImageInArray());
                                                 }
                                                 }
