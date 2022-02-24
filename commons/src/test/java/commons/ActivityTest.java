package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity activity1;
    private Activity activity2;
    private Activity activity3;

    @BeforeEach
    void setUp(){
        activity1 = new Activity("123", "title", 230, "some site");
        activity2 = new Activity("123", "title", 230, "some site");
        activity3 = new Activity("1233", "title", 230, "some site");
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
}