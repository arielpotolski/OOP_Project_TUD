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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HighestConsumptionQuestionTest{
	private Activity activity1;
	private Activity activity2;
	private Activity activity3;
	private HighestConsumptionQuestion question;

	@BeforeEach
	void setup() throws IOException {
		this.activity1 = new Activity("123", "act1", 100, "pathpng1", "first site");
		this.activity2 = new Activity("456", "act2", 230, "pathpng2", "second site");
		this.activity3 = new Activity("789", "act3", 360, "pathpng3", "third site");
		this.question = new HighestConsumptionQuestion(
			this.activity1,
			this.activity2,
			this.activity3
		);
	}

	@Test
	public void constructorTest(){
		assertNotNull(this.question);
	}

	@Test
	public void getActivity1TitleTest(){
		assertEquals("act1", this.question.getActivity1Title());
	}

	@Test
	public void getActivity2TitleTest(){
		assertEquals("act2", this.question.getActivity2Title());
	}

	@Test
	public void getActivity3TitleTest(){
		assertEquals("act3", this.question.getActivity3Title());
	}

	@Test
	public void getActivity1ImagePathTest() {
		assertEquals("pathpng1", this.question.getActivity1ImagePath());
	}

	@Test
	public void getActivity2ImagePathTest() {
		assertEquals("pathpng2", this.question.getActivity2ImagePath());
	}

	@Test
	public void getActivity3ImagePathTest() {
		assertEquals("pathpng3", this.question.getActivity3ImagePath());
	}

	@Test
	public void wrongAnswerTest() {
		assertEquals(0, this.question.pointsEarned(1000, 1, 15.0, false));
	}

	@Test
	public void correctAnswerTest1() {
		assertEquals(500, this.question.pointsEarned(1000, 3, 0.5, false));
	}

	@Test
	public void equalsTestTrue() {
		HighestConsumptionQuestion question2 = new HighestConsumptionQuestion(
			this.activity1,
			this.activity2,
			this.activity3
		);

		assertEquals(this.question, question2);
		assertEquals(this.question.hashCode(), question2.hashCode());
	}

	@Test
	public void equalsTestFalse1() {
		int i = 20;
		assertNotEquals(this.question, i);
	}

	@Test
	public void equalsTestFalse() throws IOException {
		Activity activity4 = new Activity("123", "act4", 100, "pathpng4", "first site");
		HighestConsumptionQuestion question2 = new HighestConsumptionQuestion(
			this.activity2,
			this.activity3,
			activity4
		);
		assertNotEquals(this.question, question2);
	}

	@Test
	public void returnEnergyConsumption() {
		assertEquals(100L, this.question.returnEnergyConsumption("act1"));
		assertEquals(230L, this.question.returnEnergyConsumption("act2"));
		assertEquals(360L, this.question.returnEnergyConsumption("act3"));
	}

	@Test
	public void imageInByteArrayActivity1() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				HighestConsumptionQuestionTest
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.question.imageInByteArrayActivity1());
	}

	@Test
	public void imageInByteArrayActivity2() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				HighestConsumptionQuestionTest
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.question.imageInByteArrayActivity2());
	}

	@Test
	public void imageInByteArrayActivity3() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				HighestConsumptionQuestionTest
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.question.imageInByteArrayActivity3());
	}
}