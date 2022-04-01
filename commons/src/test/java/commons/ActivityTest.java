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
	void setup() throws IOException {
		this.activity1 = new Activity("123", "title", 230, "pathpng", "some site");
		this.activity2 = new Activity("123", "title", 230, "pathpng", "some site");
		this.activity3 = new Activity("1233", "title", 230, "pathjpg", "some site");
	}

	@Test
	public void checkConstructor() {
		assertNotNull(this.activity1);
	}

	@Test
	void getId() {
		assertEquals("123", this.activity1.getId());
	}

	@Test
	void getTitle() {
		assertEquals("title", this.activity1.getTitle());
	}

	@Test
	void getConsumptionInWh() {
		assertEquals(230, this.activity1.getConsumptionInWh());
	}

	@Test
	void getImagePath() {
		assertEquals("pathpng", this.activity1.getImagePath());
	}

	@Test
	void getSource() {
		assertEquals("some site", this.activity1.getSource());
	}


	@Test
	void setId() {
		this.activity1.setId("234");
		assertEquals("234", this.activity1.getId());
	}

	@Test
	void setTitle() {
		this.activity1.setTitle("title2");
		assertEquals("title2", this.activity1.getTitle());
	}

	@Test
	void setConsumptionInWh() {
		this.activity1.setConsumptionInWh(420);
		assertEquals(420, this.activity1.getConsumptionInWh());
	}


	@Test
	void setImagePath() {
		this.activity1.setImagePath("imagegif");
		assertEquals("imagegif", this.activity1.getImagePath());
	}

	@Test
	void setSource() {
		this.activity1.setSource("some other site");
		assertEquals("some other site", this.activity1.getSource());
	}

	@Test
	void testEqualsMethod() {
		assertEquals(this.activity1, this.activity2);
		assertNotEquals(this.activity1, this.activity3);
	}

	@Test
	void testHashMethod() {
		assertEquals(this.activity1.hashCode(), this.activity2.hashCode());
	}

	@Test
	void makeFake() {
		assertEquals(this.activity1, this.activity2);
		this.activity1.makeFake(List.of(40L, 30L));
		assertNotEquals(this.activity1, this.activity2);
		assertNotEquals(30L, this.activity1.getConsumptionInWh());
		assertNotEquals(40L, this.activity1.getConsumptionInWh());
	}

	@Test
	void toStringTest() {
		String expected =
			"ID: 123\nTitle: title\nConsumption in Wh: 230\nImage Path: pathpng\nSource: some site";
		assertEquals(expected, this.activity1.toString());
	}

	@Test
	void getImageInArray() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				Activity
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);
		assertArrayEquals(bos.toByteArray(), this.activity1.getImageInArray());
	}

	@Test
	void setImageInArray() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				Activity
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);
		this.activity1.setImageInArray(new byte[] {0, 1});
		assertArrayEquals(new byte[] {0, 1}, this.activity1.getImageInArray());
	}
}