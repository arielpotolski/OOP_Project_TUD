package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HighestConsumptionQuestionTest{

	private Activity activity1;
	private Activity activity2;
	private Activity activity3;
	private HighestConsumptionQuestion question;

	@BeforeEach
	void setUp() throws IOException {
		activity1 = new Activity("123", "act1", 100,
				"pathpng1", "first site");
		activity2 = new Activity("456", "act2", 230,
				"pathpng2", "second site");
		activity3 = new Activity("789", "act3", 360,
				"pathpng3", "third site");
		question =
				new HighestConsumptionQuestion(activity1, activity2, activity3);
	}

	@Test
	public void constructorTest(){
		assertNotNull(question);
	}

	@Test
	public void getActivity1TitleTest(){
		assertEquals("act1", question.getActivity1Title());
	}

	@Test
	public void getActivity2TitleTest(){
		assertEquals("act2", question.getActivity2Title());
	}

	@Test
	public void getActivity3TitleTest(){
		assertEquals("act3", question.getActivity3Title());
	}

	@Test
	public void getActivity1ImagePathTest(){
		assertEquals("pathpng1", question.getActivity1ImagePath());
	}

	@Test
	public void getActivity2ImagePathTest(){
		assertEquals("pathpng2", question.getActivity2ImagePath());
	}

	@Test
	public void getActivity3ImagePathTest(){
		assertEquals("pathpng3", question.getActivity3ImagePath());
	}

	@Test
	public void wrongAnswerTest(){
		assertEquals(0, question.pointsEarned(1000, 1,
				15.0));
	}

	@Test
	public void correctAnswerTest1(){
		assertEquals(500, question.pointsEarned(1000,
				3, 0.5));
	}

	@Test
	public void equalsTestTrue(){
		HighestConsumptionQuestion question2 =
				new HighestConsumptionQuestion(activity1, activity2, activity3);

		assertTrue(question.equals(question2));
		assertEquals(question.hashCode(), question2.hashCode());
	}

	@Test
	public void equalsTestFalse1(){
		int i = 20;

		assertFalse(question.equals(i));
	}

	@Test
	public void equalsTestFalse() throws IOException {
		Activity activity4 = new Activity("123", "act4", 100,
				"pathpng4", "first site");
		HighestConsumptionQuestion question2 =
				new HighestConsumptionQuestion(activity2, activity3, activity4);
		assertFalse(question.equals(question2));
	}

	@Test
	public void returnEnergyConsumption() {
		assertEquals(100L, question.returnEnergyConsumption("act1"));
		assertEquals(230L, question.returnEnergyConsumption("act2"));
		assertEquals(360L, question.returnEnergyConsumption("act3"));
	}

	@Test
	public void imageInByteArrayActivity1() throws IOException {
		BufferedImage bufferedImage = ImageIO
				.read(Objects
						.requireNonNull(HighestConsumptionQuestionTest
								.class
								.getClassLoader()
								.getResourceAsStream("IMGNotFound.jpg")));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, ".jpg", bos);

		assertArrayEquals(bos.toByteArray(),
				question.imageInByteArrayActivity1());
	}

	@Test
	public void imageInByteArrayActivity2() throws IOException {
		BufferedImage bufferedImage = ImageIO
				.read(Objects
						.requireNonNull(HighestConsumptionQuestionTest
								.class
								.getClassLoader()
								.getResourceAsStream("IMGNotFound.jpg")));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, ".jpg", bos);

		assertArrayEquals(bos.toByteArray(),
				question.imageInByteArrayActivity2());
	}

	@Test
	public void imageInByteArrayActivity3() throws IOException {
		BufferedImage bufferedImage = ImageIO
				.read(Objects
						.requireNonNull(HighestConsumptionQuestionTest
								.class
								.getClassLoader()
								.getResourceAsStream("IMGNotFound.jpg")));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, ".jpg", bos);

		assertArrayEquals(bos.toByteArray(),
				question.imageInByteArrayActivity3());
	}
}